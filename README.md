judcon-cricket
==============

#Forge Script
```

new-project --named judcon-cricket --topLevelPackage org.jboss.aerogear.judconcricket 
persistence setup --provider HIBERNATE --container JBOSS_AS7
entity --named Match --package org.jboss.aerogear.judconcricket.model
field string --named description
field string --named title
field string --named teamOne
field string --named teamTwo
field string --named score
 
entity --named Comment --package org.jboss.aerogear.judconcricket.model
field string --named description
field string --named title
field string --named author
field string --named content
field manyToOne --named match --fieldType org.jboss.aerogear.judconcricket.model.Match.java
cd ..
pickup Match
field oneToMany --named comments --fieldType org.jboss.aerogear.judconcricket.model.Comment.java
 
forge git-plugin https://github.com/sebastienblanc/angularjs-scaffoldx-plugin --ref topcoat
 
rest setup --activatorType APP_CLASS
rest endpoint-from-entity --contentType application/json org.jboss.aerogear.judconcricket.model.* --strategy ROOT_AND_NESTED_DTO 
cd ..
scaffold-x setup --scaffoldType angularjs
scaffold-x from *

```


