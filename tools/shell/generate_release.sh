#!/bin/bash

version=$1

pmd_top_dir=~/tmp
pmd_bin_dir=$pmd_top_dir/pmd-$version
pmd_src_dir=$pmd_top_dir/pmd-$version
pmd_tmp_dir=$pmd_top_dir/pmd-tmp

echo
echo "Rebuilding everything"
echo

cd ../..
ant dist

echo
echo "Press [enter] to generate docs"

read RESP
mvn site

cd etc

echo
echo "generating binary file $pmd_top_dir/pmd-bin-$version.zip"
echo

rm -rf $pmd_bin_dir
rm -f $pmd_top_dir/pmd-bin-$version.zip
mkdir -p $pmd_bin_dir/etc
mkdir $pmd_bin_dir/bin
mkdir $pmd_bin_dir/lib
mkdir -p $pmd_bin_dir/java14/lib
mkdir $pmd_bin_dir/java14/bin
cp ../LICENSE.txt changelog.txt $pmd_bin_dir/etc
cd ../bin/
cp pmd.* cpd.sh cpdgui.bat designer.* $pmd_bin_dir/bin
cd ../etc/
cp ../java14/lib/*.jar $pmd_bin_dir/java14/lib/
cp ../java14/bin/cpd* ../java14/bin/pmd.* ../java14/bin/designer.* $pmd_bin_dir/java14/bin/
chmod 755 $pmd_bin_dir/java14/bin/*
cp ../lib/pmd-$version.jar ../lib/asm-3.1.jar ../lib/jaxen-1.1.1.jar ../lib/junit-4.4.jar $pmd_bin_dir/lib/
mkdir $pmd_bin_dir/etc/xslt
cp xslt/*.xslt xslt/*.js xslt/*.gif xslt/*.css $pmd_bin_dir/etc/xslt/
mkdir $pmd_bin_dir/docs
cp -R ../target/site/* $pmd_bin_dir/docs
cd $pmd_top_dir
zip -q -r pmd-bin-$version.zip pmd-$version/
cd -

echo
echo "binary package generated"
echo

release_tag=`echo $version|sed -e "s/\./_/g"`

echo
echo
echo "Type \"yes\" to tag svn repository using 'pmd_release_$release_tag'"

read TAG_RESP;

if [ "$TAG_RESP" = "yes" ]; then
	echo
	echo "Tagging release using"
	echo "svn copy -m \"$version release tag\" https://pmd.svn.sourceforge.net/svnroot/pmd/trunk/pmd  https://pmd.svn.sourceforge.net/svnroot/pmd/tags/pmd/pmd_release_$release_tag"
	echo
	svn copy -m "$version release tag" https://pmd.svn.sourceforge.net/svnroot/pmd/trunk/pmd  https://pmd.svn.sourceforge.net/svnroot/pmd/tags/pmd/pmd_release_$release_tag
else
	echo
	echo "Skipping svn tag!!!"
	echo
fi



echo "generating source file $pmd_top_dir/pmd-src-$version.zip"

rm -rf $pmd_src_dir
rm -f $pmd_top_dir/pmd-src-$version.zip
cd ..
ant jarsrc
if [ "$TAG_RESP" = "yes" ]; then
	svn -q export https://pmd.svn.sourceforge.net/svnroot/pmd/tags/pmd/pmd_release_$release_tag $pmd_src_dir
else
	svn -q export https://pmd.svn.sourceforge.net/svnroot/pmd/trunk/pmd  $pmd_src_dir
fi
cp lib/pmd-src-$version.jar $pmd_src_dir/lib/
cp lib/pmd-$version.jar $pmd_src_dir/lib
mkdir $pmd_src_dir/docs
cp -R target/site/* $pmd_src_dir/docs
rm -f $pmd_src_dir/tools/config/clover2.license
cd $pmd_top_dir
zip -q -r pmd-src-$version.zip pmd-$version/
cd -

echo
echo "source package generated"
echo

echo "Use the command below to upload to sourceforge"
echo
echo "lftp -e \"mput -O incoming/ $pmd_top_dir/pmd-src-$version.zip $pmd_top_dir/pmd-bin-$version.zip\" upload.sourceforge.net"

