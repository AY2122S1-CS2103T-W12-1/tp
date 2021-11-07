---
layout: page
title: Ong Xing Wei's Project Portfolio Page
---

### Project: SportsPA

SportsPA is a desktop application used to manage membership and training sessions of NUS sports CCAs.
The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the ability to import member details from a CSV file.
  * What it does: Allows the user to add and update the details of multiple members without having to repeatedly add members individually from the CLI.
  * Justification: This feature improves user experience of the product because instead of repeatedly typing the command word and related fields with their prefixes into the CLI, 
  a user will be able to reduce the amount he/she types by simply preparing a CSV file and importing the member details from the CSV file.
  * Highlights: The implementation was challenging as it required a way to get the details of existing members without
  a reference to the member objects such as an index like most of the other commands.
  * Pull request: [\#137](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/137)

* **New Feature**: Added the ability to export facility details and member allocations to a CSV file.
  * What it does: Allows the user to convert the facility details and member allocations into a readable format.
  * Justification: This feature improves the product significantly because the user will have a way to share the facility details and allocations 
  with their CCA members without the need for members to actually have the SportsPA application.
  * Highlights: The implementation was somewhat challenging as it required analysis of how the data should be presented in the CSV file.
  * Pull request: [\#170](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/170)

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/#breakdown=true&search=moley456)

* **Team tasks**:
  * Set up the GitHub team org and repo 
  * Managed releases `v1.2` - `v1.2b` (2 releases) on GitHub

* **Enhancements to existing features**:
  * Added support for saving and loading facility data to and from the json data file.
  * Updated the GUI to accommodate another tab for the facility list and also increased result window height to 
  accommodate for longer error messages(Pull requests [\#117](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/117), [\#223](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/223))
  * Wrote additional tests for existing features to increase coverage from 88% to 92% (Pull requests [\#36](), [\#38]())

* **Documentation**:
  * User Guide:
    * Added documentation for the features `export` and `import`: [\#137](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/137) [\#173](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/173)
    * Did cosmetic tweaks to the table of content and introduction headers: [\#155](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/155)
  * Developer Guide:
    * Added use cases for new features from v1.3 [\#250](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/250)
    * Added instructions for manual testing [\#249](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/249) 
    * Updated Developer Guide to include Facilities.

* **Community**:
  * Reported bugs and gave suggestions to other teams through [PE-D](https://github.com/Moley456/ped/issues)
  
