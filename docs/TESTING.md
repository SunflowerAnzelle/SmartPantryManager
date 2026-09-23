\# Smart Pantry Manager - Testing Documentation



\## 1. Purpose



Testing was performed to verify that the main Smart Pantry Manager features work correctly and that pantry data is stored and retrieved correctly.



The application was tested on an Android Emulator using the completed Java Android application.



\## 2. Functional Testing



| Test                                              | Expected Result                                                            | Result |

| ------------------------------------------------- | -------------------------------------------------------------------------- | ------ |

| Add pantry item with valid information            | Item is saved and displayed in the pantry list                             | Pass   |

| Add pantry item with missing required information | Validation message is displayed and item is not saved                      | Pass   |

| Add pantry item with invalid quantity             | Validation prevents invalid input                                          | Pass   |

| Edit pantry item                                  | Existing information can be changed and saved                              | Pass   |

| Delete pantry item                                | Selected item is removed from the pantry list                              | Pass   |

| View pantry list                                  | Saved pantry items are displayed using RecyclerView                        | Pass   |

| Close and reopen application                      | Previously saved pantry data remains available                             | Pass   |

| Open Suggested Recipes                            | Recipes matching the current pantry are displayed                          | Pass   |

| Missing ingredient                                | Recipe requiring the missing ingredient is excluded                        | Pass   |

| Insufficient quantity                             | Recipe is excluded when the pantry quantity is below the required quantity | Pass   |

| Unit conversion                                   | Supported unit differences are handled during matching                     | Pass   |

| Singular/plural ingredient names                  | Simple singular and plural differences are handled                         | Pass   |

| No matching recipes                               | Appropriate zero-match feedback is displayed                               | Pass   |

| Open recipe details                               | Full ingredients and preparation steps are displayed                       | Pass   |

| Settings screen                                   | Settings screen opens correctly                                            | Pass   |

| Bottom navigation                                 | Navigation between main application screens works correctly                | Pass   |



\## 3. Strict Recipe Matching



Strict matching was specifically tested to ensure that recipes are not suggested when the user does not have everything required.



For example, when an ingredient or sufficient quantity is unavailable, the recipe is excluded from the main Suggested Recipes list.



A recipe only becomes available when all required ingredients and quantities are present.



\## 4. Database Persistence



SQLite persistence was tested by adding pantry ingredients, closing the application, and reopening it.



The previously saved pantry information remained available after reopening the application, confirming that the data is stored persistently rather than only being held temporarily in memory.



\## 5. User Interface and Navigation



The main screens and navigation controls were tested to ensure that the user can move between the Home, Pantry, Suggested Recipes, Recipe Details, and Settings screens.



The application also provides validation feedback when required pantry information is not entered correctly.



\## 6. Testing Summary



The completed tests confirmed that the main functional requirements of Smart Pantry Manager operate as expected, including pantry CRUD operations, persistent SQLite storage, strict recipe matching, recipe details, validation, and navigation.



