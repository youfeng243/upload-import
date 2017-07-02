#!/usr/bin/env bash

project=upload-import
main_class=com.haizhi.UploadImportServer

check() {
    pid=`ps -ef | grep java | grep "$1" | grep "$2" | awk '{print $2}'`
    if [ -z "${pid}" ]; then
        exit
    fi

    echo ${pid}
}

if [ $# == 2 ]; then
    echo $(check $1 $2)
else
    echo $(check ${project} ${main_class})
fi