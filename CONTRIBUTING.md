# Contribution
Pour faciliter la contribution à ce projet, des règles ont été mises en place.

## Règles 
- Les variables doivent utiliser le <a href="https://fr.wikipedia.org/wiki/Camel_case">**camel case**</a> et porter des noms évoquant leur utilité.
- Effectuer des commits réguliers et correctement documentés.
- Commenter le code.
- Respect de principes comme <a href="https://fr.wikipedia.org/wiki/Ne_vous_r%C3%A9p%C3%A9tez_pas">**DRY**</a> (*Don't Repeat Yourself*) et <a href="https://fr.wikipedia.org/wiki/Principe_KISS">**KISS**</a> (*Keep It Simple, Stupid*).

## Mise en place du projet sur un IDE
### Eclipse :
- Ouvrir Eclipse
- Aller dans ```File``` puis ```Open project from File System```
- Importer la source du projet ```pokae-ProfRaoult``` en tant que **Directory**
- Veiller à ce que le projet importe uniquement ```pokae-ProfRaoult```

Si le dossier "project" ou le dossier "project/src" n'est pas reconnu en tant que dossier source, modifier le ```Build Path``` du dossier ```project``` et cliquer sur "Use a Source folder".

Si le dossier "interfaces" ou les interfaces ne sont pas reconnu alors ajouter le dossier au "Build Path"

Si l'option pour modifier le Build Path n'est pas disponible, aller dans les propriétés du projet (Clique droit sur ```pokae-ProfRaoult``` et "properties") dans "Project Facets" cliquer sur le texte pour l'activer et vérifier que "JAVA" est bien cocher.
 
- Il ne manque plus qu'à **importer la bibliothèque JUnit**. Pour se faire, faire un clic-droit sur le projet puis cliquer sur *Properties*, puis *Java Build Path*, puis *Libraries* et enfin cliquer sur *Classpath*, puis sur *Add Library*, puis *JUnit*, *Next >*, *Finish*, et pour terminer l'opération *Apply and Close*.  

TODO

### IntelliJ : 
TODO

## Gestion des branches
Chaque développeur doit avoir sa branche qu'il doit garder la plus propre possible pour permettre une division des charges de programmation.
 

