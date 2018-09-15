Java_jcoinche_2017
===================

Client/server solution in order to play Coinche (a version of the French card game, Belote). Spring MVC and WebSocket 

####Ce projet consistait en l'élaboration d'un jeu de coinche, au travers d'une architecture client/serveur et du langage de programmation Java.

----------
Déroulement du jeu
================
### **Règles**
La belote coinchée est un jeu de contrat qui se joue par équipe de deux. Les plis remportés par les joueurs d’une même équipe sont donc à additionner. A la fin d’une partie, on compte le nombre de points des cartes contenus dans les plis remportés. On sait alors si l’équipe qui a pris est dedans ou a rempli son contrat.
**Règles du jeu** :
- Distribution de 3 puis 2 cartes
- Découverte de l'Atout
- Tour de table pour désigner le preneur
- 2ème tour de table si le preneur n'a pas été désigné
- Ditribution de 3 cartes pour les joueurs lambda et 2 cartes pour le preneur qui a pris l'Atout
- Début du jeu

**Le Jeu** :
Le joueur qui se trouve à gauche du donneur débute la manche. Chaque joueur est dans l’obligation de suivre la couleur demandée. Si un joueur ne peut pas fournir de carte de la couleur demandée, il doit alors couper à l’atout. Au cas où il n’a pas d’atout, il doit pisser, c’est-à-dire jouer une autre carte dans une autre couleur.
Il est également possible de ne pas jouer de l’atout dans le cas où le partenaire est maître du pli alors que l’on ne possède pas de cartes de la couleur demandée.
Le gagnant d’un pli le ramasse et débute le prochain tour. Quand le cas ou deux joueurs coupent, le second joueur doit obligatoirement surcouper, c’est-à-dire fournir un atout plus fort que celui qui est déjà sur la table. C’est le cas également pour les autres joueurs suivant. Dans le cas où le joueur n’a pas d’atout plus fort, il doit quand même jouer un atout plus bas si bien sûr il en possède un dans son jeu.
###**Lancement et Jouabilité du Programme**
- Lancer le serveur à l'aide de la commande : `java -jar target/Server_JCoinche-0.1.0.jar`
- Lancer le client à l'aide de la commande :  `java -jar target/Client_JCoinche-0.1.0.jar [PORT] [URL]`
- Attendre le remplissage de la salle de jeu (pour tester le projet, lancer 4 clients)
> **Note:** Les clients ont parfois quelques difficultés à se lancer. Si l'un des 4 clients lancés n'affiche pas "HANDSHAKE SUCCEED", veillez à relancer l'ensemble des clients. Un des client doit aussi afficher la proposition "Get Asset", une fois ces deux conditions remplies, le jeu en réseau est fonctionnel.
- Pour jouer une carte, respecter la syntaxe suivante : [TYPE]-[value]

###**Protocole et déroulé du programme**

Utilisation de Spring MVC et de WebSocket avec une surcouche de Stomp.
Le client subscribe à des channels et écrits sur des endpoints. Le Serveur en réaction à l'écriture d'un Client, va répondre à une Room.
Voici la liste des interactions Client/Serveur. Les informations transident via des Objets Java.

- Handshake: Client subscribe à `/users/{id}` et ping avec un objet "Message" `/greeting/{id}`. Le client sera alors ajouter à une room. Le client recevra un objet "Greeting".
- Récupération d'information sur la tâche à effectuer. Client subscribe à `/info/{id}`, il va ping toutes les secondes le serveur sur `/users/askForTask/{id}`. Le client recevra un objet "ProtoTask" qui lui indiquera qu'elle sera sa prochaine opération à effectuer.
- Récupération de l'Atout: Client subscribe à `/getAsset/{id}` et ping `/getAsset/{id}`. Il recevra un objet "Card".
- Récupération des Cartes après distribution: Client subscribe à `/takeCards/{id}` et ping `/takeCards/{id}`. Il recevra un objet "Card".
- Récupération du Board avant de "PutCard". Client subscribe à `/board/{id}` et ping `/board/{id}`. Il recevra un objet "Board".
- Récupération du retour suite à une pose de carte. Client subscribe à `/putCard/{id}` et ping `/putCard/{id}`. Il recevra un objet "PutCard".
- Récupération du résultat de la partie. Client subscribe à `/end/{id}` et ping `/end/`. Les clients recevront un objet "ScoreBoard".

###**Bug à fixer**
Il y a parfois un problème de retour d'informations lors du Handshake. Le serveur reçoit bien un message sur `/greeting/{id}`. Malheureusement le client ne reçoit parfois pas d'Objet en réponse dans le handleFrame dédié au la Channel `/users/{id}`   
