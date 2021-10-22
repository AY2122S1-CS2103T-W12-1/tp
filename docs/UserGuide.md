---
layout: page
title: User Guide
---

SportsPA is a desktop application used to manage membership and training sessions of NUS sports CCAs.

* Table of Contents
{:toc}

_____________________________________________________________________________________________________________

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `SportsPA.jar` from [here](https://github.com/AY2122S1-CS2103T-W12-1/tp/releases/tag/v1.2.1).

1. Copy the file to the folder you want to use as the _home folder_ for SportsPA.

1. Double-click the file to start the app. The GUI similar to the image below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`listf`** : Lists all facilities.

   * **`addf`**`n/Court 1 l/University Sports Centre t/1700 c/10` : Adds the venue named `Court 1` to the facilities list.

   * **`deletem`**`3` : Deletes 3rd member in the members list.

   * **`clearm`** : Clears the members list

   * **`exit`** : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Characters with the slash symbols are used to indicate the type of parameter supplied by user.
  <br> eg. in `addm n/NAME p/PHONE_NUMBER`, `n/` and `p/` are the symbols used before entering a parameter
  for `NAME` and `PHONE_NUMBER` respectively.
  
* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `findm KEYWORD`, `KEYWORD` is a parameter which can be used as `findm Ben`.

* Items in square brackets are optional.<br>
  e.g `n/NAME p/PHONE_NUMBER [d/DAY(S)] [t/TAG]` can be used as `n/Ben p/91111111 d/1` or as `n/John p/91111111` or as `n/John p/91111111 t/exco`.
  
* Items with `...` after them can be used multiple times including zero times.<br>
  e.g. `t/TAG]...` can be used as ` `(i.e. 0 times), `t/exco`, `t/exco t/y2`etc.
  
* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME l/LOCATION`, `l/LOCATION n/NAME` is also acceptable.
  
* If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of the parameter wll be taken.<br>
  e.g. if you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.
  
* Extraneous parameters for commands that do not take in parameters (such as `help`, `listf`, `clearm` and `exit`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

</div>

### Getting help: `help`

Shows message explaining how to access the help page.

Format: `help`

### Adding a facility: `addf`

Adds a facility to the facility list.

Format: `addf n/NAME l/LOCATION t/TIME c/CAPACITY`


<div markdown="block" class="alert alert-info">
   
**:information_source: Note:** Facilities with the same `NAME` and `LOCATION` are considered duplicates and cannot exist in the facility list together.
</div>

* `TIME` specifies the start time and is to be inputted in the format HH:MM
* `CAPACITY` specifies the maximum allowed people in the facility
* All fields are required

Examples:
`addf n/Court 1 l/University Sports Hall t/1500 c/5` adds Court 1 at University Sports Hall at 3pm with a capacity of 5

### Listing all facilities : `listf`

Shows a list of all facilities.

Format: `listf`

### Finding a facility : `findf`

Finds facilities whose location contains any of the given keywords.

Format: `findf KEYWORD [MORE_KEYWORDS]`
* Search is case-insensitive. Eg. `Utown` will match `utown`
* Only the location is searched
* Only full words will be matched eg. `Utown` will not match `town`
* Facilities matching at least one keyword will be returned (i.e. OR search) e.g `Utown Redhill` will return `Utown Field`, `Redhill Sports Complex`

Examples:
* `find redhill` returns `Redhill Sports Complex` and `Redhill Field`
* `find utown redhill` returns `Utown Field`, `Redhill Sports Complex` and `Redhill Field`
  
### Deleting a facility : `deletef`

Deletes a facility from the facility list.

Format: `deletef INDEX`
* Deletes the facility at the specified `INDEX`
* `INDEX` refers to the index number shown in the displayed facility list
* `INDEX` **must be a positive integer** 1, 2, 3… 

Examples:
* `listf` followed by `deletef 2` deletes the 2nd person in the facility list
* `findf Court 1` followed by `deletef 1` deletes the 1st facility in the results of the findf command

### Editing a facility: `editf`

Edits an existing facility from the facility list.

Format: `editf INDEX [n/NAME] [l/LOCATION] [t/TIME] [c/CAPACITY]
* Edits the facility at the specified `INDEX`
* `INDEX` refers to the index number shown in the displayed facility list
* `INDEX` **must be a positive integer** 1, 2, 3… 
* At least one of the optional fields must be provided
* Existing values will be updated to the input values

Examples:
* `editf 1 n/Court 5` edits the name of the 1st facility to be `Court 5`
* `editf 2 n/Court 20 l/University Sports Hall` edits the name and location of the 2nd facility to be `Court 20` and `University Sports Hall` respectively

### Adding a member: `addm`

Adds a member to the members list

Format: `addm n/NAME p/PHONE_NUMBER [d/DAY(S)] [t/TAG]...`
* `[d/DAY(S)]` is an optional field indicating a list of days for which the member is available for that week
* `[t/TAG]` is an optional field indicating the tags associated with the member.
* `1` represents Monday, `2` represents Tuesday … and `7` represents Sunday
* Members added without availability will have an empty list of days

<div markdown="block" class="alert alert-info">
   
**:information_source: Note:** Members with the same `NAME` are considered duplicates and cannot exist in the member list together
</div>

Examples:
* `addm n/John p/91234567 d/1 3 5` adds John to the member list and indicates his availability on Monday, Tuesday
   and Friday.
  
* `addm n/John p/91234567 t/exco t/y2` adds John to the member list and tags him as 'exco' and 'y2'.
* `addm n/Bob p/91228372` adds Bob to the member list with zero available days by default.

### Listing all members: `listm`

Shows a list of all members

Format: `listm`

### Sorting member list: `sortm`

Shows a list of all members, sorted alphabetically

Format: `sortm`

### Finding a member `findm`

Finds members whose names contains any of the given keywords

Format: `findm KEYWORD [MORE_KEYWORDS]`
* Search is case-insensitive. Eg. `John` will match `john`
* `[MORE_KEYWORDS]` is an optional field
* Only the name of the member is searched
* Only full words will be matched eg. `Johnny` will not match `John`
* Names matching at least one keyword will be returned (i.e. OR search) e.g `John Henry` will return `John, Henry`

Examples:
* `findm Bob` returns `bob` and `Bob Doe`
* `findm john bobby` returns `John Lee`, `Bobby Tan`
  
### Deleting a member : `deletem`

Deletes a member from the member list

Format: `deletem INDEX`

* Deletes the member at the specified `INDEX`. 
* The index refers to the index number shown in the displayed member list. 
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `listm` followed by `deletem 2` deletes the member at index 2 of the member list
* `findm John` followed by `deletem 1` deletes the 1st person in the results of the `findm` command 

### Setting member availability: `setm`

Sets the availability of given member(s).

Format: `setm INDEX/INDICES d/DAY(S)`

* Sets the availability of the member(s) at the specified `INDEX/INDICES` to be the specified `DAY(s)`
* Availability is defined as days of the week when member is free
* `DAY` **must be a positive integer from 1 to 7**, whereby 1 represents Monday and 7 represents Sunday.
* `DAYS` **must be separated by a single space** 1 2 3 …​
* `INDEX` refers to the index number shown in the displayed member list
* `INDICES` **must be positive integers** 1, 2, 3, …​
* `INDICES` **must be separated by a single space** 

Examples:
* `listm` followed by `setm 5 d/1 2` sets the availability of the person at index 5 in the member list to be Monday and Tuesday
* `findm John` followed by `setm 2 d/1` sets the availability of the person at index 2 in the results of the `findm` command to be Monday

### Splitting members into facilities : `split`

Splits members into facilities based on its capacity and members' availability.

Format: `split DAY`

* Allocate members available at the specified `DAY` to each facility
* `DAY` **must be a positive integer from 1 to 7**, whereby 1 represents Monday and 7 represents Sunday.

Examples:
* `split 1` splits members into groups for training on Monday of that week and displays the list of allocations to the user

### Clearing all entries in facility list: `clearf`

Clears all entries from the facility list.

Format: `clearf`

### Clearing all entries in member list: `clearm`

Clears all members from the member list.

Format: `clearm`

### Creating an alias: `alias`

Creates a shortcut name for any command.

Format: `alias s/SHORTCUT cw/COMMAND_WORD`

* Creates an alias that allows the `COMMAND_WORD` to be executed with the given `SHORTCUT`
* `SHORTCUT` must not an existing command
* `COMMAND_WORD` **must be an existing command**

<div markdown="block" class="alert alert-info">
   
**:information_source: Note:** If an alias with the given `SHORTCUT` already exists, the newly created alias will replace the existing one.
</div>

Examples:
* `alias s/lf cw/listf` creates an alias for `listf` command. Entering `lf` will call the `listf` command and a list of all facilities will be shown.

### Listing all aliases: `aliases`

Shows a list of all created aliases.

Format: `aliases`

### Deleting an alias: `unalias`

Deletes the given user defined alias.

Format: `unalias SHORTCUT`

* Deletes the alias associated with the specified `SHORTCUT`

Examples:
* `unalias lf` deletes the alias with shortcut name `lf`.

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

* SportsPA data are saved in the hard disk automatically after any command that changes the data. They are saved as a JSON file `[JAR file location]/data/sportspa.json`

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If changes made to the data file makes its format invalid, SportsPA will discard all data and start with an empty data file at the next run.
</div>


--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous SportsPA home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add facility**| `addf n/NAME l/LOCATION t/TIME c/CAPACITY` <br> eg. `addf n/Court 1 l/University Sports Hall t/1500 c/5`
**Add member**| `addm n/NAME p/PHONE_NUMBER [d/DAY(S)] [t/TAG]` <br> eg. `addm n/John Doe p/91111111`, `addm n/John Doe p/91111111 d/1 3 5`, `addm n/John Doe p/91111111 d/1 3 5 t/exco`
**Clear facilities**|`clearf`
**Clear member**| `clearm`
**Delete facility**| `deletef INDEX` <br> eg. `deletef 4`
**Delete member**| `deletem INDEX` <br> eg. `deletem 1`
**Exit**| `exit`
**Find member**| `findm KEYWORD` <br> eg. `findm John`, `findm John Bob`
**Find facility**| `findf KEYWORD` <br> eg. `findf Clementi`, `findf Utown`
**Help**| `help`
**List members**| `listm`
**List facilities**| `listf`
**Set member availability**| `setm INDEX/INDICES d/DAY(S)...` <br> eg.`setm 1 2 3 d/2 3 5`
**Split members**| `split d/DAY` <br> eg. `split d/1`
**Creates alias**| `alias s/SHORTCUT cw/COMMAND_WORD` <br> eg. `alias s/lf cw/listf`
**List aliases**| `aliases` 
**Deletes alias**| `unalias SHORTCUT` <br> eg. `unalias lf`
