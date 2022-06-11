# Projet Pokemon
 La SAE Pokemon

## Membres de l'équipe

- <a href="https://github.com/Nytuo">BEUX Arnaud</a>
- <a href="https://github.com/Aehnt">TESTA Ethan</a>
- <a href="https://github.com/Elyan-Gruau">GRUAU Elyan</a>
- <a href="https://github.com/ZaY-eZ">MAHE Jules</a>


## Présentation de la SAE

Les Pokémons sont des créatures possédant des types différents, et font partis du jeu éponyme de la société japonaise *Nintendo*. <br/>
Ce jeu est un RPG tour par tour avec une histoire, les Pokémons peuvent être sauvages ou appartenir à des dresseurs qui organisent des combats entre eux.

Dans cette SAE, nous allons nous concentrer uniquement sur l'aspect des combats : les attaques, les avantages de types, le gain d'experience grâce aux combats, la montée de niveau... Pas d'histoire, d'objets ni de capture de Pokémon. 


## Répartition du travail 
### Rendu 1
- Génération du diagramme UML : Arnaud 
- Description des classes utilisées : Arnaud, Ethan 
- Séparation des classes en packages : Arnaud, Ethan, Elyan, Jules


### Rendu 2
- Capacite, Pokedex, Pokemon : Arnaud
- Pokemon, Pokedex : Ethan
- Readme, contributing, PDF : Elyan 
- Aide de classes, MD : Jules
- JavaDoc : Arnaud
- Tests : Arnaud

### Rendu 3
- IAsimple : Elyan, Jules
- Echange : Jules
- MainGame : Arnaud
- Tour, Combat et Dresseur : Jules, Arnaud (Correction de bugs)
- PDF, Intructions(MD) : Ethan 
- JavaDoc : Arnaud
- Tests : N/A

### Rendu 4
- Modifications graphiques: Elyan
- Tests:
- PDF:

## Mise en place du projet pour le développement :
- Dans un dossier de votre choix cloner le repository Github avec la commande suivante (vous aurez besoin de Git sur votre machine) : 

``` git clone https://github.com/IUT-DEPT-INFO-UCA/pokae-ProfRaoult.git```
- Ouvrer le dossier *pokae-ProfRaoult* avec votre IDE ou éditeur de texte préféré.
- Configurer le au besoin et commencer à coder.  



## Aide sur l'import du projet dans votre IDE :

### Sur l'IDE Eclipse :
- Ouvrir Eclipse.
- Aller dans *File* puis *Open project from File System*.
- Importer le dossier ***pokae-ProfRaoult*** (le dossier que vous avez cloner depuis GitHub).
- Veiller à ce que le projet importe **uniquement* ***pokae-ProfRaoult***.
- Il ne manque plus qu'à **importer la bibliothèque JUnit**. Pour se faire, faire un clic-droit sur le projet puis cliquer sur *Properties*, puis *Java Build Path*, puis *Libraries* et enfin cliquer sur *Classpath*, puis sur *Add Library*, puis *JUnit*, *Next >*, *Finish*, et pour terminer l'opération *Apply and Close*.

Si le projet n'est pas directement utilisable dans Eclispe, essayer ces solutions aux problèmes connus : 

- Si l'option pour modifier le Build Path n'est pas disponible, aller dans les propriétés du projet (Clique droit sur *pokae-ProfRaoult* et *properties*) dans *Project Facets* cliquer sur le texte pour l'activer et vérifier que **JAVA** est bien cocher.
- Si le dossier *pokae-ProfRaoult/project/src* n'est pas reconnu en tant que dossier source, modifier le *Build Path* de ce dossier et cliquer sur **Use a Source folder**.
- Si le dossier *interfaces* ou si les interfaces ne sont pas reconnu alors ajouter le dossier au *Build Path* avec *Add Folder*.
 
### IntelliJ : 

- Ouvrer IntelliJ
- Ouvrer le dossier contenant le projet *pokae-ProfRaoult*
- Définir les dossiers sources suivants : *pokae-ProfRaoult/project/src* et *pokae-ProfRaoult/project/interfaces* (clique droit sur le dossier puis *mark as* puis *source Folder*.
- Configurer les JDK si ce dernier n'est pas déjà configurer. (Soit par le bandeau en haut de la fenètre, soit depuis *File* > *Project Structure* dans la catégorie *Project* et enfin choisisser la version du JDK qui vous convient (JDK Java 8 minimum), vous pouvez en importer d'autres en cliquant sur *Edit*.
- Configurer JUnit comme dans le section suivante.
#### Importer JUnit avec MAVEN
- Aller dans *File* > *Project Structure*
- Dans la catégorie *Libraries* Ajouter en une depuis MAVEN, dans la fenêtre qui suit taper "org.jupiter.junit" et dans les résultats de la recherche sélectionner la dernière version stable.

## Contribution
Vous trouverez <a href="https://github.com/IUT-DEPT-INFO-UCA/pokae-ProfRaoult/blob/main/CONTRIBUTING.md"> ci-joint </a> le lien concernant la contribution du projet.


