@echo off
rem /**
rem  * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
rem  *
rem  * Author: ThinkGem@163.com
rem  */
title %cd%
echo.
echo [��Ϣ] ʹ��Jetty������й��̡�
echo.
rem pause
rem echo.

cd %~dp0
cd..

set MAVEN_OPTS=%MAVEN_OPTS% -Xms512m -Xmx1024m -XX:PermSize=256m -XX:MaxPermSize=512m
call mvn jetty:run

cd bin
pause