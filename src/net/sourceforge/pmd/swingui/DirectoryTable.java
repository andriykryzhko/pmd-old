package net.sourceforge.pmd.swingui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Font;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Donald A. Leckie
 * @since August 25, 2002
 * @version $Revision$, $Date$
 */
class DirectoryTable extends JTable
{

    private boolean m_sortAscending = true;

    /**
     ********************************************************************************
     *
     * @param directoryTree
     */
    protected DirectoryTable(DirectoryTree directoryTree)
    {
        super(new DirectoryTableModel(directoryTree));

        setShowGrid(false);
        setRowSelectionAllowed(false);
        setColumnSelectionAllowed(false);
        setCellSelectionEnabled(true);
        setBackground(PMDLookAndFeel.TABLE_BACKGROUND_COLOR);

        TableColumnModel columnModel = getColumnModel();
        JTableHeader tableHeader = getTableHeader();

        columnModel.setColumnMargin(10);
        tableHeader.addMouseListener(new TableHeaderMouseListener());

        //
        // Create the column renderers.
        //
        ColumnHeaderRenderer headerRenderer;
        DefaultTableCellRenderer cellRenderer;
        TableColumn column;

        //
        // Create renderers for the file name column.
        //
        column = columnModel.getColumn(DirectoryTableModel.FILE_NAME_COLUMN);
        headerRenderer = new ColumnHeaderRenderer();
        cellRenderer = new DefaultTableCellRenderer();

        headerRenderer.setHorizontalAlignment(JLabel.LEFT);
        column.setHeaderRenderer(headerRenderer);
        cellRenderer.setHorizontalAlignment(JLabel.LEFT);
        column.setCellRenderer(cellRenderer);

        //
        // Create a cell renderer for the file size column.
        //
        column = columnModel.getColumn(DirectoryTableModel.FILE_SIZE_COLUMN);
        headerRenderer = new ColumnHeaderRenderer();
        cellRenderer = new DefaultTableCellRenderer();

        headerRenderer.setHorizontalAlignment(JLabel.RIGHT);
        column.setHeaderRenderer(headerRenderer);
        cellRenderer.setHorizontalAlignment(JLabel.RIGHT);
        column.setCellRenderer(cellRenderer);

        //
        // Create a cell renderer for the file last modified date column.
        //
        column = columnModel.getColumn(DirectoryTableModel.FILE_LAST_MODIFIED_COLUMN);
        headerRenderer = new ColumnHeaderRenderer();
        cellRenderer = new DefaultTableCellRenderer();

        headerRenderer.setHorizontalAlignment(JLabel.LEFT);
        column.setHeaderRenderer(headerRenderer);
        cellRenderer.setHorizontalAlignment(JLabel.LEFT);
        column.setCellRenderer(cellRenderer);
    }

    /**
     ********************************************************************************
     *
     * @return
     */
    protected File getSelectedSourceFile()
    {
        ListSelectionModel selectionModel = getSelectionModel();
        int row = selectionModel.getAnchorSelectionIndex();
        DirectoryTableModel tableModel = (DirectoryTableModel) getModel();

        return tableModel.getFile(row);
    }

    /**
     ********************************************************************************
     ********************************************************************************
     ********************************************************************************
     */
    private class ColumnHeaderRenderer extends DefaultTableCellRenderer
    {

        private Font m_boldFont;
        private Border m_border;

        /**
         *********************************************************************************
         *
         */
        protected ColumnHeaderRenderer()
        {
            super();

            Font oldFont;
            BevelBorder bevelBorder;
            EtchedBorder etchedBorder;
            EmptyBorder emptyBorder;

            oldFont = getFont();
            m_boldFont = new Font(oldFont.getName(), Font.BOLD, oldFont.getSize());
            bevelBorder = new BevelBorder(BevelBorder.RAISED);
            etchedBorder = new EtchedBorder(EtchedBorder.RAISED);
            emptyBorder = new EmptyBorder(0, 5, 0, 5);
            m_border = new CompoundBorder(etchedBorder, bevelBorder);
            m_border = new CompoundBorder(m_border, emptyBorder);
        }

        /**
         *********************************************************************************
         *
         * @param table
         * @param value
         * @param isSelected
         * @param hasFocus
         * @param row
         * @param column
         *
         * @return
         */
        public Component getTableCellRendererComponent(JTable table,
                                                       Object value,
                                                       boolean isSelected,
                                                       boolean hasFocus,
                                                       int row,
                                                       int column)
        {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            setFont(m_boldFont);
            setBorder(m_border);
            setBackground(Color.blue);
            setForeground(Color.white);

            return this;
        }
    }

    /**
     ********************************************************************************
     ********************************************************************************
     ********************************************************************************
     */
    private class TableHeaderMouseListener extends MouseAdapter
    {

        /**
         ****************************************************************************
         *
         * @param event
         */
        public void mouseReleased(MouseEvent event)
        {
            int sortColumn;
            JTableHeader tableHeader;
            Point mouseLocation;
            Rectangle columnHeaderArea;

            m_sortAscending = !m_sortAscending;
            mouseLocation = event.getPoint();
            tableHeader = getTableHeader();
            sortColumn = tableHeader.columnAtPoint(mouseLocation);

            if (sortColumn >= 0)
            {
                // When the cursor is resizing a column, the mouse released event will
                // reach here.  To prevent sorting happening during resizing, the column
                // header area is inset on the left and right by 10 pixels each.
                columnHeaderArea = tableHeader.getHeaderRect(sortColumn);
                columnHeaderArea.x -= 10;
                columnHeaderArea.width -= 20;

                if (columnHeaderArea.contains(mouseLocation))
                {
                    sort(sortColumn, m_sortAscending);
                }
            }
        }

        /**
         ****************************************************************************
         *
         * @param sortColumn
         */
        protected void sort(int sortColumn, boolean sortAscending)
        {
            TableSortComparator comparator;
            DirectoryTableModel tableModel;
            Vector tableData;
            int rowCount;
            Vector[] rows;

            comparator = new TableSortComparator(sortColumn, sortAscending);
            tableModel = (DirectoryTableModel) getModel();
            tableData = tableModel.getDataVector();
            rowCount = tableData.size();
            rows = new Vector[rowCount];
            rows = (Vector[]) tableData.toArray(rows);

            Arrays.sort(rows, comparator);

            for (int n = 0; n < rowCount; n++)
            {
                tableData.set(n, rows[n]);
            }

            tableModel.fireTableDataChanged();
        }
    }

    /**
     ********************************************************************************
     ********************************************************************************
     ********************************************************************************
     */
    private class TableSortComparator implements Comparator
    {

        private int m_sortColumn;
        private boolean m_sortAscending;

        /**
         ****************************************************************************
         *
         * @param sortColumn
         * @param sortAscending
         */
        private TableSortComparator(int sortColumn, boolean sortAscending)
        {
            m_sortColumn = sortColumn;
            m_sortAscending = sortAscending;
        }

        /**
         ****************************************************************************
         *
         * @param keyA
         * @param keyB
         *
         * @int result
         */
        public int compare(Object rowA, Object rowB)
        {
            String keyA = (String) ((Vector) rowA).get(m_sortColumn);
            String keyB = (String) ((Vector) rowB).get(m_sortColumn);

            if (m_sortAscending)
            {
                return keyA.compareTo(keyB);
            }

            return keyB.compareTo(keyA);
        }

        /**
         ****************************************************************************
         *
         * @param object
         */
        public boolean equals(Object object)
        {
            return (object == this);
        }
    }
}