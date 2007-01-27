@echo off
set TOPDIR=%~dp0/..
set VERSION=3.9
set PMDJAR=%TOPDIR%/lib/pmd-%VERSION%.jar
set JARPATH=%TOPDIR%/lib/asm-3.0.jar;%TOPDIR%/lib/jaxen-1.1.jar
set OPTS=
set MAIN_CLASS=net.sourceforge.pmd.util.viewer.Viewer

java %OPTS% -cp %PMDJAR%;%JARPATH%;%TOPDIR%/build %MAIN_CLASS% %*

