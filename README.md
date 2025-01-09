### Programmation multi-agents avec JADE

[(version web)](https://emmanueladam.github.io/jade/)  
[(version française)](https://github.com/EmmanuelADAM/jade/tree/master/)

Cas d'étude pour ISSIA'23.

---
## Économie circulaire

Voici un scénario :
- Un utilisateur possède des produits.
- Ces produits peuvent tomber en panne ou devenir obsolètes.
- Plusieurs solutions existent :
  - Tutoriels en ligne,
  - Cafés de réparation,
  - Magasins de pièces détachées,
  - Distributeurs.

Lorsqu’un produit ne fonctionne plus, l’utilisateur cherche de l’aide :
- D'abord par ses propres moyens,
- Ensuite localement (café de réparation),
- Puis dans une région plus large,
- Enfin auprès des distributeurs.

Des solutions peuvent exister sur internet, mais l’utilisateur peut avoir besoin d’un café de réparation pour comprendre et réparer son produit.

### Spécifications pour un scénario simple :
1. **Types de produits** :
  - Cafetière : 3 pièces, environ 40 €
  - Lave-linge : 3 pièces, environ 200 €
  - Souris d’ordinateur : 3 pièces, environ 40 €
  - Aspirateur : 3 pièces, environ 100 €
  - Lave-vaisselle : 4 pièces, environ 200 €

2. **Exemples de produits** :
  - Cafetière10 : une cafetière avec un prix majoré de 10 %.
  - LaveVaisselle-30 : un lave-vaisselle avec un prix réduit de 30 %.

3. **Pièces amovibles ou réparables** :
  - Un produit contient entre 1 et 4 pièces identifiables (ex. Cafetière10-1).
  - Une panne touche une seule pièce, et peut être :
    - Très légère (0), facile (1), moyenne (2), difficile (3), définitive (4).
  - Le niveau de panne est détecté lors de la réparation.

4. **Compétences des utilisateurs** :
  - Niveau 0 : Incapable de comprendre ou réparer.
  - Niveau 1 : Comprend des pannes de niveau 1, répare le niveau 0.
  - Niveau 2 : Comprend et répare jusqu’au niveau 2.
  - Niveau 3 : Comprend et répare jusqu’au niveau 3.

5. **Cafés de réparation** :
  - Spécialisés par type de produit.
  - Possèdent seulement 4 pièces des types 1, 2 ou 3 ; aucune pièce de type 4.
  - Compétences de réparation de niveau 2 ou 3.

6. **Magasins de pièces détachées** :
  - Spécialisés par type de produit.
  - Possèdent 10 pièces des types 1, 2 ou 3 ; aucune pièce de type 4.

7. **Distributeurs** :
  - Pas de restrictions sur les produits disponibles.

8. **Tarifs** :
  - Cafés de réparation : 5 à 15 €/pièce (seconde main).
  - Magasins de pièces détachées : 20 à 40 €/pièce.
  - Distributeurs : 50 à 70 €/pièce.

9. **Limites** :
  - Les utilisateurs ont un budget limité.
  - Ils recherchent un café de réparation adapté à leur produit.
  - En cas d’échec, ils peuvent demander une pièce au magasin ou acheter un nouveau produit.

---
### Objectif
Concevoir et programmer des agents utilisant la bibliothèque JADE pour simuler ce comportement.
- Générer des objets aléatoires pour les agents utilisateurs.
- Générer des pièces aléatoires pour les cafés de réparation et les magasins.

Les cafés de réparation peuvent échanger des pièces entre eux (1 jour/échange).

### Critères supplémentaires
- Flexibilité pour fixer un rendez-vous.
- Flexibilité pour la réception de pièces ou produits.

---
### Code
- Le code fourni est fonctionnel. Il peut être lancé via la classe `launch.Launch`.
- Les utilisateurs reçoivent des produits. Les agents RepairCoffeeAgents et PartstoreAgents reçoivent des pièces (davantage pour les SparestoreAgents). Les distributeurs reçoivent des produits.
- Un utilisateur lance un appel d’offre vers les RepairCoffeeAgents. Cet appel n’est qu’un exemple et ne fait rien de concret.