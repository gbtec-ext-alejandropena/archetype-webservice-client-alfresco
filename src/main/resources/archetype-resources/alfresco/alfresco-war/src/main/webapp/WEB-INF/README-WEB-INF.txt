#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
${symbol_pound}    Licensed to the Apache Software Foundation (ASF) under one or more
${symbol_pound}    contributor license agreements.  See the NOTICE file distributed with
${symbol_pound}    this work for additional information regarding copyright ownership.
${symbol_pound}    The ASF licenses this file to You under the Apache License, Version 2.0
${symbol_pound}    (the "License"); you may not use this file except in compliance with
${symbol_pound}    the License.  You may obtain a copy of the License at
${symbol_pound}    
${symbol_pound}    http://www.apache.org/licenses/LICENSE-2.0
${symbol_pound}    
${symbol_pound}    Unless required by applicable law or agreed to in writing, software
${symbol_pound}    distributed under the License is distributed on an "AS IS" BASIS,
${symbol_pound}    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
${symbol_pound}    See the License for the specific language governing permissions and
${symbol_pound}    limitations under the License.

Readme for the WEB-INF overlay procedures
-----------------------------------------
- Note:
This folder contents will be overlayed to the ${symbol_dollar}{webapp.name}/WEB-INF folder. So for example a web.xml file put into this folder will overwrite (not "override" by classpath or inheritance rules) the existing web.xml.
This is useful for example for configuring external SSO authentication filters (e.g. NTLMAuthenticationFilter)
