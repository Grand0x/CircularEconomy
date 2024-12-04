<meta name="description" content="Programmation multi-agent en Java : utilisation d'une version mise à jour de la plateforme Jade. Matériaux pour le tutoriel Jade : communication, protocoles, votes, services, comportements, ..." />

# Programmation d'agents avec Jade

[(version web)](https://emmanueladam.github.io/jade/)
[(version française)](https://github.com/EmmanuelADAM/jade/tree/master/)

Étude de cas pour ISSIA'23.

----
## Économie circulaire

Voici un scénario :

- certains utilisateurs possèdent des produits
- certains produits peuvent être cassés ou devenir obsolètes
- plusieurs solutions existent :
    - tutoriels en ligne
    - repair cafés
    - magasins de pièces détachées
    - distributeurs.

- Lorsque le produit ne fonctionne plus, l'utilisateur essaie de trouver de l'aide : par lui-même, localement (repair café), dans une région plus large, et enfin auprès des distributeurs.

- Des solutions peuvent exister en ligne, mais l'utilisateur peut avoir besoin d'un repair café pour comprendre et réparer son produit.

Nous pouvons ajouter ces spécifications pour un scénario simple :
- un produit a de 1 à 4 éléments amovibles/réparables, que nous appelons el1, el2, el3, el4.
- une panne concerne un élément.
- une panne peut être très légère (0), facile (1), moyenne (2), difficile (3), définitive (4)
    - ce niveau de problème est détecté pendant la réparation
- un utilisateur a une compétence de réparation, cette compétence peut être de niveau :
    - 0 (incapable de réparer lui-même et de comprendre),
    - 1 (peut comprendre une panne de niveau jusqu'à 1, mais ne peut réparer que le niveau 0),
    - 2 (peut comprendre et réparer une panne de niveau jusqu'à 2),
    - 3 (peut comprendre et réparer une panne de niveau jusqu'à 3),

- les repair cafés n'ont que 4 éléments des types 1, 2 ou 3 et n'ont pas d'éléments de type 4
- les magasins de pièces détachées n'ont que 10 éléments des types 1, 2 ou 3 et n'ont pas d'éléments de type 4
- les distributeurs n'ont pas de problème d'éléments

- les repair cafés ont un coût de 10€/élément (seconde main)
- les magasins de pièces détachées ont un coût de 30€/élément
- les distributeurs ont un coût de 60€/élément

- un utilisateur a une quantité d'argent limitée.
    - nous supposons qu'il/elle choisit d'aller au repair café et si la réparation est impossible là-bas :
        - il/elle passe au niveau supérieur (un magasin de pièces détachées)
        - ou arrête et laisse l'élément de son produit au repair café.

---
Concevez et construisez des agents utilisant la nouvelle bibliothèque Jade pour simuler ce comportement.
- Générer un objet aléatoire pour les agents utilisateurs,
- et des éléments aléatoires dans le magasin et le repair café.

---
Les repair cafés peuvent interagir entre eux pour échanger des pièces.
- cela a un coût en temps... 1 jour/élément

Nous ajoutons un second critère, le temps :
- les repair cafés ont un coût de 2 jours/élément (ils vous apprennent à réparer, et l'élément ne s'adapte pas très bien)
- les magasins de pièces détachées ont un coût de 1 jour (ils ont la pièce spécifique bien adaptée mais vous avez besoin d'un jour pour l'installer)
- les distributeurs ont un coût de 1 jour (ils échangent simplement l'élément)

---