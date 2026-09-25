\# Smart Pantry Manager - Database Documentation



\## 1. Database Overview



Smart Pantry Manager uses a local SQLite database called `smart\_pantry.db`.



The database is managed by the `DatabaseHelper` class, which extends Android's `SQLiteOpenHelper`. The current database version is 2.



SQLite was chosen because the application stores pantry and recipe information locally on the user's device. The application does not need an external database server for its main functions.



\## 2. Database Structure



The database contains three main tables:



\* `pantry\_items`

\* `recipes`

\* `recipe\_ingredients`



The `pantry\_items` table stores the ingredients entered by the user. The `recipes` table stores the recipes available in the application, while `recipe\_ingredients` stores the ingredients and quantities required by each recipe.



The recipe tables are connected using the recipe ID.



\## 3. Pantry Items



The `pantry\_items` table stores the ingredients that the user currently has available.



Each pantry item has an automatically generated ID, an ingredient name, a quantity, a unit and an optional expiry date.



For example, a user could add:



```text

Name: Eggs

Quantity: 6

Unit: pieces

Expiry Date: 2026-10-05

```



The ingredient name, quantity and unit are required when the item is saved. The expiry date can be left empty.



The application uses the database to add, retrieve, update and delete pantry items. When pantry items are retrieved, they are ordered alphabetically by name.



\## 4. Recipes



The `recipes` table stores the recipes available in Smart Pantry Manager.



Each recipe has an automatically generated ID, a recipe name and preparation steps.



The application includes default recipes such as:



\* Tomato Omelette

\* Cheese Sandwich

\* Chicken Pasta

\* Vegetable Rice

\* Tomato Pasta

\* Chicken Sandwich

\* Scrambled Eggs

\* Rice and Chicken

\* Easy Pancakes

\* Fried Rice

\* Macaroni and Mince

\* Tomato and Cucumber Salad



The preparation instructions are stored with each recipe so that they can be displayed on the recipe details screen.



\## 5. Recipe Ingredients



The `recipe\_ingredients` table stores the ingredients required for each recipe.



Each record contains the recipe ID, ingredient name, required quantity and unit.



For example, the Chicken Pasta recipe has ingredient records for pasta, chicken, onion, cooking oil, salt, black pepper and mixed herbs.



The `recipe\_id` connects each ingredient record to its recipe in the `recipes` table.



This allows one recipe to have multiple required ingredients.



\## 6. Database Relationship



The database uses a relationship between the `recipes` and `recipe\_ingredients` tables.



Each recipe can have multiple ingredient records, while each ingredient record belongs to one recipe. This creates a \*\*one-to-many relationship\*\* between the two tables.



The relationship can be represented as:



```text

recipes

\----------------

id (PK)

name

steps

&#x20;    |

&#x20;    | 1

&#x20;    |

&#x20;    |------< 

&#x20;             |

&#x20;             | many

&#x20;             v

recipe\_ingredients

\-------------------

id (PK)

recipe\_id (FK)

ingredient\_name

required\_quantity

unit

```



The `recipe\_id` field in `recipe\_ingredients` is a foreign key that refers to the `id` field in the `recipes` table.



For example, the `Chicken Pasta` recipe has one record in the `recipes` table and several related records in `recipe\_ingredients`:



```text

Chicken Pasta

&#x20;     |

&#x20;     +---- Pasta

&#x20;     +---- Chicken

&#x20;     +---- Onion

&#x20;     +---- Cooking Oil

&#x20;     +---- Salt

&#x20;     +---- Black Pepper

&#x20;     +---- Mixed Herbs

```



The `pantry\_items` table is not directly linked to the recipe tables through a foreign key. Instead, the application compares the pantry items with the recipe ingredients when determining which recipes can be prepared from the user's available ingredients.



The `recipe\_ingredients` table also uses `ON DELETE CASCADE`. This means that when a recipe is deleted, its related recipe ingredient records can also be removed.



\## 7. How the Database Supports Recipe Matching



The database supports the Suggested Recipes feature by keeping the user's pantry information separate from the recipe requirements.



The general process is:



```text

pantry\_items

&#x20;    |

&#x20;    | Compare ingredient names,

&#x20;    | quantities and supported units

&#x20;    v

recipe\_ingredients

&#x20;    |

&#x20;    | Check whether all required

&#x20;    | ingredients are available

&#x20;    v

recipes

&#x20;    |

&#x20;    v

Suggested Recipes

```



For a recipe to be suggested, the application checks the ingredients and quantities available in the pantry against the requirements stored in `recipe\_ingredients`.



For example, if a recipe requires five eggs and the pantry contains only four eggs, the recipe is not considered available.



This allows the database to support the application's strict recipe-matching behaviour.



\## 8. Database Operations



The `DatabaseHelper` class contains the main database operations used by the application.



For pantry management, it provides methods to:



\* Add a pantry item

\* Retrieve all pantry items

\* Update a pantry item

\* Delete a pantry item



For recipes, it provides methods to:



\* Retrieve all recipes

\* Retrieve the ingredients for a selected recipe



The pantry list is retrieved from SQLite and ordered by ingredient name.



Recipe ingredients are retrieved using the selected recipe's ID.



\## 9. Default Recipe Data



The default recipes are inserted when the database is first created.



The `insertDefaultRecipes()` method creates the recipe records and then adds their required ingredients to the `recipe\_ingredients` table.



This means the application has recipe data available without requiring the user to manually create recipes before using the Suggested Recipes feature.



The database currently contains 18 default recipes.



\## 10. Database Versioning



The database currently uses version \*\*2\*\*.



The `onUpgrade()` method is used when the database version changes.



When an older version is upgraded to version 2, the application adds the default recipe data.



Using `SQLiteOpenHelper` allows the database to be managed when the application is updated without having to create a completely separate database class.



\## 11. Data Persistence



Pantry information is stored in the SQLite database rather than only being kept while the application is running.



When a user adds an ingredient, the information is inserted into the `pantry\_items` table.



When an ingredient is edited or deleted, the corresponding database record is updated or removed.



This allows the pantry information to remain available when the application is closed and opened again.



\## 12. Database Implementation



The database functionality is implemented in:



```text

app/src/main/java/com/example/smartpantrymanager/DatabaseHelper.java

```



The `DatabaseHelper` class is responsible for creating the database tables, handling database upgrades, inserting the default recipes and providing the methods used by the application to store and retrieve pantry and recipe information.



