# Rapport

# Laboratoire n°3 : Environnement I Codes-barres, iBeacons et NFC

## 👨🏻‍💻Auteurs:

- Werkle Johann
- Tailhades Laurent
- Zeller Corentin

## Balises NFC

### Questions - réponses

> Dans la manipulation ci-dessus, les tags NFC utilisés contiennent 4 valeurs textuelles codées
en UTF-8 dans un format de message NDEF. Une personne malveillante ayant accès au porteclés peut aisément copier les valeurs stockées dans celui-ci et les répliquer sur une autre puce
NFC. A partir de l’API Android concernant les tags NFC3
, pouvez-vous imaginer une autre approche
pour rendre plus compliqué le clonage des tags NFC ? Existe-il des limitations ? Voyez-vous
d’autres possibilités ?
> 

Les messages NDEF n’ont pas de protection pour l’écriture ou lecture on ne peut donc pas empecher de copier un tag NFC. Pour palier a ce problème il y a des identifiants qui sont read-only mais ca n’empeche pas de lire ces données.
Dans l’API Android on peut remarquer que des type de NFC MIFARE sont disponible et permettent de lire des partitions a l’aide de clé mais cela n’empeche toujours pas la réplication mais uniquement la lecture en clair. Donc pour pouvoir avoir un systeme plus avancé il faudrait un mini-ordinateur capable de faire un mechanisme plus sure.

[https://developer.android.com/guide/topics/connectivity/nfc/advanced-nfc](https://developer.android.com/guide/topics/connectivity/nfc/advanced-nfc)

> Est-ce qu’une solution basée sur la vérification de la présence d’un iBeacon sur l’utilisateur,
par exemple sous la forme d’un porte-clés serait préférable ? Veuillez en discuter.
> 

Cela permetterait d’augmenter le niveau de sécurité par la compléxité de la mise en oeuvre mais les iBeacons font juste de l’emission donc on peut tout a fait “émuler” un iBeacon et le problème de clonage est ducoup le même.

## Codes-barres

### Questions - réponses

> Quelle est la quantité maximale de données pouvant être stockée sur un QR-code ? Veuillez
expérimenter, avec le générateur conseillé5 de codes-barres (QR), de générer différentes
tailles de QR-codes. Pensez-vous qu’il est envisageable d’utiliser confortablement des QRcodes complexes (par exemple du contenant >500 caractères de texte, une vCard très
complète ou encore un certificat Covid) ?

Selon les info trouvées sur le site qrcode.com,

Les codes QR standard peuvent contenir jusqu'à 3 Ko de données.

Les codes QR sont constitués de plusieurs lignes et colonnes. La combinaison de ces lignes et colonnes forme une grille de modules (carrés). Il peut y avoir au maximum 177 lignes et 177 colonnes, ce qui signifie que le nombre maximal possible de modules est de 31 329. À l'œil nu, ce ne sont que de petits carrés qui ne signifient pas grand-chose, mais la disposition exacte de ces modules permet au code QR de coder ses données. Cela signifie que, contrairement aux codes-barres traditionnels qui sont unidimensionnels et utilisent une rangée de lignes, les codes QR utilisent deux dimensions, ce qui leur permet de stocker beaucoup plus de données dans le même espace.

Après quelques tests, on se rend compte qu'il n'y a pas de problèmes à travailler avec des qr code de très grande taille, ici avec 2000 charactères et en utilisant notre application. Après on peut imaginer que tout les téléphones n'ont pas de bon smartphone avec une camera assez correct (ici un pixel 5). 

![image](https://user-images.githubusercontent.com/58049740/146684971-e0063f5f-1e59-477b-ad3b-b6451c36aae2.png)
![image](https://user-images.githubusercontent.com/58049740/146684298-c918dea7-c70e-4062-a3eb-ec896f08be11.png)

Peut être que ça peut commencer à poser problème avec une carte de visite qui comprend des informations beaucoup trop compliqué.



> Il existe de très nombreux services sur Internet permettant de générer des QR-codes
dynamiques. Veuillez expliquer ce que sont les QR-codes dynamiques. Quels sont les avantages
et respectivement les inconvénients à utiliser ceux-ci en comparaison avec des QR-codes
statiques. Vous adapterez votre réponse à une utilisation depuis une plateforme mobile

Un code QR dynamique est un code QR dans lequel est encodée une courte URL de redirection. L'information que vous essayez de communiquer n'est pas encodée dans le code QR lui-même, comme dans le cas d'un code QR statique. Au lieu de cela, l'information que vous essayez de communiquer se trouve sur un site Web, et un code QR dynamique redirige vers ce site. Et le meilleur ? L'URL de redirection peut changer. Contrairement à un code QR statique, les informations contenues dans un code QR dynamique peuvent changer sans qu'un nouveau code soit nécessaire. Lorsque l'on compare un code QR à un code-barres, c'est l'un des principaux avantages.

Si les codes QR statiques ne sont pas idéaux pour le monde des affaires ou du marketing en raison de leur manque d'adaptabilité, ils fonctionnent bien pour un usage personnel car vous ne suivez probablement pas les mesures de votre campagne.
Ils sont idéaux pour fournir des informations détaillées lors d'événements ponctuels et de campagnes marketing uniques. De plus, ils n'ont pas besoin de connection au réseau pour pouvoir afficher des informations.

## Balises iBeacon

### Questions - réponses

> Les iBeacons sont très souvent présentés comme une alternative à NFC. Vous commenterez cette
affirmation en vous basant sur 2-3 exemples de cas d’utilisations (use-cases) concrets (par exemple epaiement, second facteur d’identification, accéder aux horaires à un arrêt de bus, etc.).
> 

**Balise pour les Musées**

Pour des informations publiques qui peuvents êtres constamments émisents aux personnes a proximitées, les beacons sont la solutions préférée car le nfc à une portée très courte et les restrictions sur IOS moins grandes. Mais les tags NFC sont pratique dans des lieux qui peuvent avoir une grandes concentrations dd’information comme par exemple les musées. C’est plus comparable a un Qr code qui n’est pas influencé par les conditions lumineuse (qui peuvent etre sombre dans les musées). De plus il n’y a pas besoin de changer les piles contrairement au beacons.

[https://www.mabalise.be/2021/09/27/comment-la-nfc-va-sublimer-lexperience-des-visiteurs-de-votre-musee-exposition-ou-galerie-dart/](https://www.mabalise.be/2021/09/27/comment-la-nfc-va-sublimer-lexperience-des-visiteurs-de-votre-musee-exposition-ou-galerie-dart/)

**Twint**:

Twint utilise les iBeacons car le NFC est restreint par Apple et les Iphone ont une très grandes part de marché en Suisse. Quand on paye le terminal de paiement ne communique pas directement avec le smartphone mais il sert de lien a la transaction qui se fait dans un serveur de backend ou se rejoigne la transaction que le beacon veut faire payer et le smartphone du client qui a les informations bancaires.

L'application TWINT transmet l'ID de la balise reçue au serveur TWINT. Le serveur identifie le point de vente et le client peut lui être attribué dans le backend. Ensuite, le serveur renvoie un message de confirmation au smartphone, qui est affiché dans l'application. En même temps, le point de vente apprend de la balise qu'un couplage a eu lieu et demande les informations de fidélité du client au système TWINT.

Les communications sensibles ne se font donc surtout pas par les beacons qui sont juste lié au point de vente.

[https://www.adnovum.ch/en/company/blog/projects/twint_secure_payment_by_smartphone.html](https://www.adnovum.ch/en/company/blog/projects/twint_secure_payment_by_smartphone.html)
