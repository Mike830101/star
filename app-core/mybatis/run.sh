#!/bin/bash
#
java -classpath mybatis-generator-core-1.4.2.jar org.mybatis.generator.api.ShellRunner -configfile GeneratorConfig.xml -overwrite -verbose
