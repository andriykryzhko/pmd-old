@echo off
set CVSROOT=:ext:tomcopeland@cvs.sourceforge.net:/cvsroot/pmd
set CVS_RSH=c:\data\bin\ssh\ssh
set HOME=c:\data
set ANT_HOME=c:\ant
set PATH=c:\j2sdk1.4.2_04\bin;c:\ant\bin\;%PATH%
set CLASSPATH=../build/
set CLASSPATH=%CLASSPATH%;../
set CLASSPATH=%CLASSPATH%;../lib/asm-3.0.jar
set CLASSPATH=%CLASSPATH%;../lib/backport-util-concurrent.jar
set CLASSPATH=%CLASSPATH%;../lib/jaxen-1.1-beta-10.jar
set CLASSPATH=%CLASSPATH%;c:\javacc2.1\bin\lib\JavaCC.zip
set CLASSPATH=%CLASSPATH%;c:\ant\lib\ant.jar
set CLASSPATH=%CLASSPATH%;c:\ant\lib\junit.jar
