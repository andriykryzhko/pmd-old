/*
 * User: tom
 * Date: Jul 1, 2002
 * Time: 2:22:25 PM
 */
package net.sourceforge.pmd;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import java.io.InputStream;
import java.io.Reader;
import java.io.IOException;
import java.util.*;

public class RuleSetFactory {

    /**
     * Returns an Iterator of RuleSet objects
     */
    public Iterator getRegisteredRuleSets() throws RuleSetNotFoundException {
        try {
            Properties props = new Properties();
            props.load(getClass().getClassLoader().getResourceAsStream("rulesets/rulesets.properties"));
            String rulesetFilenames = props.getProperty("rulesets.filenames");
            List ruleSets = new ArrayList();
            for (StringTokenizer st = new StringTokenizer(rulesetFilenames, ","); st.hasMoreTokens();) {
                RuleSet ruleSet = createRuleSet(st.nextToken());
                ruleSets.add(ruleSet);
            }
            return ruleSets.iterator();
        } catch (IOException ioe) {
            throw new RuntimeException("Couldn't find rulesets.properties; please ensure that the rulesets directory is on the classpath.  Here's the current classpath: " + System.getProperty("java.class.path"));
        }
    }

    public RuleSet createRuleSet(String name) throws RuleSetNotFoundException {
        return createRuleSet(tryToGetStreamTo(name));
    }

    public RuleSet createRuleSet(InputStream inputStream) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(inputStream);
            Element root = doc.getDocumentElement();

            RuleSet ruleSet = new RuleSet();
            ruleSet.setName(root.getAttribute("name"));
            ruleSet.setDescription(root.getChildNodes().item(1).getFirstChild().getNodeValue());

            NodeList rules = root.getElementsByTagName("rule");
            for (int i =0; i<rules.getLength(); i++) {
                Node ruleNode = (Node)rules.item(i);
                Rule rule = null;
                if (ruleNode.getAttributes().getNamedItem("ref") != null) {
                    ExternalRuleID externalRuleID = new ExternalRuleID(ruleNode.getAttributes().getNamedItem("ref").getNodeValue());
                    RuleSetFactory rsf = new RuleSetFactory();
                    RuleSet externalRuleSet = rsf.createRuleSet(getClass().getClassLoader().getResourceAsStream(externalRuleID.getFilename()));
                    rule = externalRuleSet.getRuleByName(externalRuleID.getRuleName());
                } else {
                    rule = (Rule)Class.forName(ruleNode.getAttributes().getNamedItem("class").getNodeValue()).newInstance();
                    rule.setName(ruleNode.getAttributes().getNamedItem("name").getNodeValue());
                    rule.setMessage(ruleNode.getAttributes().getNamedItem("message").getNodeValue());
                }
                ruleSet.addRule(rule);
            }
            return ruleSet;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't read from that source: " + e.getMessage());
        }
    }

    private InputStream tryToGetStreamTo(String name) throws RuleSetNotFoundException {
        InputStream in = getClass().getClassLoader().getResourceAsStream(name);
        if (in == null) {
            throw new RuleSetNotFoundException("Can't find ruleset " + name + "; make sure that path is on the CLASSPATH");
        }
        return in;
    }
}
