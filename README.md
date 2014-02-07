judcon-cricket
==============

#Forge Script
```
@/* Forge Script - Generates the judcon-cricket Application base */;

@/* Clear the screen */;
clear;

@/* Create a new project in the current directory */;
new-project --named judcon-cricket --topLevelPackage org.jboss.aerogear.judconcricket 

@/* This means less typing. If a script is automated, or is not meant to be  active, use this command */;
set ACCEPT_DEFAULTS true;


persistence setup --provider HIBERNATE --container WILDFLY

@/* create new entity: Match */;
entity --named Match --package org.jboss.aerogear.judconcricket.model
field string --named description
field string --named title
field string --named teamOne
field string --named teamTwo
field string --named score

@/* create new entity: Comment */;
entity --named Comment --package org.jboss.aerogear.judconcricket.model
field string --named description
field string --named title
field string --named author
field string --named content
field manyToOne --named match --fieldType org.jboss.aerogear.judconcricket.model.Match.java
cd ..
pick-up Match.java
field oneToMany --named comments --fieldType org.jboss.aerogear.judconcricket.model.Comment.java

@/* install angularjs plugin with support for topcoat */;
forge git-plugin https://github.com/sebastienblanc/angularjs-scaffoldx-plugin --ref topcoat

@/* Setup JAX-RS, and create CRUD endpoints */;
rest setup --activatorType APP_CLASS
rest endpoint-from-entity --contentType application/json org.jboss.aerogear.judconcricket.model.* --strategy ROOT_AND_NESTED_DTO 
cd ..

@/* Turn our Java project into a Web project with AngularJS */;
scaffold-x setup --scaffoldType angularjs
scaffold-x from *

@/* Build the project and disable ACCEPT_DEFAULTS */;
build;
set ACCEPT_DEFAULTS false;

@/* Return to the project root directory and leave it in your hands */;
cd ~~;

```

When done, please go to [Step 2](https://github.com/sebastienblanc/judcon-cricket-demo/tree/step2#judcon-cricket)

