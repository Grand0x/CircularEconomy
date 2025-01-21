### Programmation multi-agents avec JADE
GUIOT Maxime & JUAN Robin
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
Voici une structure complète pour continuer le rapport dans le README. Elle inclut une description du développement, des choix techniques, un flux de comportement en Mermaid, et une présentation de la structure du projet.

---

### Développement réalisé

Dans ce projet, nous avons développé un système multi-agents basé sur JADE pour simuler un scénario d’économie circulaire. Les agents représentent les différents acteurs (utilisateurs, cafés de réparation, magasins de pièces détachées, distributeurs) et interagissent pour résoudre des pannes ou remplacer des produits.

#### Objectifs :
1. Fournir une simulation réaliste des interactions entre agents.
2. Réaliser un flux complet : de la demande initiale à la résolution ou au remplacement du produit.
3. Offrir un comportement modulaire et adaptable en fonction des besoins.

---

### Fonctionnement de la communication entre agents

La communication entre agents suit le protocole **Contract Net Protocol (CNP)** pour négocier et proposer des solutions aux demandes des utilisateurs. Voici un aperçu des échanges :

1. **Initiation** :
    - L’agent utilisateur identifie un produit défectueux et envoie un **Call for Proposal (CFP)** à un ensemble d’agents (cafés, magasins, ou distributeurs).

2. **Propositions** :
    - Les agents recevant le CFP analysent la demande. En fonction de leurs stocks et de leurs compétences, ils répondent avec un **PROPOSE** contenant les informations nécessaires (prix, temps, etc.).

3. **Négociation** :
    - L’utilisateur choisit une offre basée sur ses contraintes (prix, délai, disponibilité) et envoie un **ACCEPT_PROPOSAL** à l’agent choisi.

4. **Finalisation** :
    - L’agent sélectionné confirme la réparation ou le remplacement via un **INFORM**. En cas de refus, un **REJECT_PROPOSAL** est envoyé.

---

### Choix techniques

- **JADE** : Permet une gestion simple des agents et des interactions basées sur FIPA-ACL.
- **Structure modulaire** : Chaque type d’agent possède des responsabilités clairement définies :
    - **UserAgent** : Initie les demandes et prend des décisions basées sur son budget et ses compétences.
    - **RepairCoffeeAgent** : Spécialisé dans les réparations de produits spécifiques avec des compétences limitées.
    - **SpareStoreAgent** : Fournit des pièces détachées avec une gestion des stocks plus importante.
    - **DistributorAgent** : Propose des produits de remplacement sans restrictions.
- **Filtrage dynamique** : Les agents utilisent des prédicats pour sélectionner les pièces ou produits adaptés.
- **Randomisation des prix** : Ajout d’une composante aléatoire pour simuler des variations réalistes.

---

### Flux de comportement des agents

[![](https://mermaid.ink/img/pako:eNqtlM1uAiEUhV-FsOlGFx13LppYbXdNjNpNO11ch4uSzgC5A_2J8YH6HH2xMow_7ahVE1nB4QDfzT1hwTMjkHe5zM17NgdybDJINQuj9NMZgZ2zxxKpN0Ptar0aQhFmThnNJrdbNZP2-jnld_rNKCY8698PU_6y3YcsQ-uGZKwpIa-svahAvEkgy6-MlISbQ6hFqhs0I7SgqB-MiCdR2fgeVs_VTyPzGllpcl95_xAqLQ0VlbVvtFRUVEz48f2V7XoJpa-vHcUZyyHUUIAW_xcwtkA4dobOwk9Ox0_OwE_Oxh-o0pGa-lDAOfyd0_k7R_l_cVWZY-32zaFcrLsfPY0kb5Zxs5HO2tIQo3EVksOGVTJ2EPd2ft3fiwMmxwCTHcD9vV138OKEnVTzFi-QClAi_EGL6kDK3RyLkMFumAqg15S3al2gBJ-7Ueg-ElLtwDwYUr0MF0HgHn_qjHcdeWxxbwU4HCgIwS14V0JebtQ7oUKRGxHj8qH-CuOP2OIW9JMx24Nk_Gy-Wi1_AAq3tbo?type=png)](https://mermaid.live/edit#pako:eNqtlM1uAiEUhV-FsOlGFx13LppYbXdNjNpNO11ch4uSzgC5A_2J8YH6HH2xMow_7ahVE1nB4QDfzT1hwTMjkHe5zM17NgdybDJINQuj9NMZgZ2zxxKpN0Ptar0aQhFmThnNJrdbNZP2-jnld_rNKCY8698PU_6y3YcsQ-uGZKwpIa-svahAvEkgy6-MlISbQ6hFqhs0I7SgqB-MiCdR2fgeVs_VTyPzGllpcl95_xAqLQ0VlbVvtFRUVEz48f2V7XoJpa-vHcUZyyHUUIAW_xcwtkA4dobOwk9Ox0_OwE_Oxh-o0pGa-lDAOfyd0_k7R_l_cVWZY-32zaFcrLsfPY0kb5Zxs5HO2tIQo3EVksOGVTJ2EPd2ft3fiwMmxwCTHcD9vV138OKEnVTzFi-QClAi_EGL6kDK3RyLkMFumAqg15S3al2gBJ-7Ueg-ElLtwDwYUr0MF0HgHn_qjHcdeWxxbwU4HCgIwS14V0JebtQ7oUKRGxHj8qH-CuOP2OIW9JMx24Nk_Gy-Wi1_AAq3tbo)

---

### Structure du projet

#### Organisation des classes
Le projet est structuré de manière modulaire pour faciliter la gestion et l’extension.

1. **Agents** :
    - `UserAgent` : Responsable de l’initiation des demandes et de la prise de décision.
    - `RepairCoffeeAgent` : Gère les réparations et répond aux demandes avec des propositions.
    - `SpareStoreAgent` : Propose des pièces détachées.
    - `DistributorAgent` : Propose des produits de remplacement complets.

2. **Objets métier** :
    - `Product` : Représente un produit avec ses caractéristiques (nom, prix, pièces).
    - `Part` : Représente une pièce détachée, avec des propriétés comme le niveau de complexité et le prix.
    - `ProductType` : Enumération des types de produits disponibles.

3. **Comportements** :
    - `CafeRepondreUtilisateur` : Définit le comportement de réponse d’un café de réparation.
    - `StoreRepondreUtilisateur` : Définit le comportement d’un magasin de pièces détachées.
    - `DistributorRepondreUtilisateur` : Définit le comportement d’un distributeur.

4. **Utilitaires** :
    - `Part.filterParts(Predicate<Part>)` : Permet de filtrer les pièces selon des critères personnalisés.
    - `RandomPriceGenerator` : Génère des prix aléatoires pour les propositions.

---

### Résumé des interactions
1. **Détection de panne** :
    - L’utilisateur identifie une panne dans un produit.
2. **Recherche de solutions** :
    - L’utilisateur contacte successivement les cafés de réparation, les magasins de pièces détachées, et les distributeurs.
3. **Résolution ou remplacement** :
    - Selon les réponses reçues, l’utilisateur choisit la meilleure offre pour résoudre son problème.
