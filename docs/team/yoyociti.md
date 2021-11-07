---
layout: page
title: Seow Xiu Wen's Project Portfolio Page
---

### Project: SportsPA

SportsPA is a desktop application used to manage membership and training sessions of NUS sports CCAs.
The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the ability to create shortcuts for commands.
    * What it does: Allows the user to create aliases for command words. Entering the user-defined shortcut for chosen command word will execute that command.
    * Justification: This feature improves user experience of the product significantly because our target users are fast typists well-versed with CLI applications. Aliasing is common in such applications, allowing our users the flexibility of defining their own command words to their preferences.
    * Highlights: Implementation was challenging for this enhancement as it required changes to how commands are parsed and commands had to be tracked.
    * Pull Request [\#124](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/124)

* **New Feature**: Added the ability to switch GUI tabs for relevant commands.
  * What it does: When users execute facility or member-related commands, the system automatically switches to that tab displaying the correct list (if it is not already on it).
  * Justification: Similar to the above new feature, this improves user experience for our target users as users need not manually click on the tabs with their mouse after executing the command.
  * Highlights: Changes had to be made to the result of a command execution so that the GUI will be able to use that info to respond accordingly.
Updating tests was also more complicated as many of the commands already relied on the existing implementation of CommandResult, thus had to consider an alternative method (i.e. utility methods) to update tests.
  * Pull Request [\#145](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/145)

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/#breakdown=true&search=yoyociti)

* **Team Tasks**:
    * Designed initial mockup for SportsPA v1.1
    * Managed release `v1.2` - `v1.2.1` (2 releases) on GitHub
    * Morphed existing product Address Book to include basic functionality supporting list of facilities (i.e `findf`, `clearf`, `deletef`, `editf`) (Pull requests [\#85](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/85), [\#89](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/89), [\#97](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/97), [\#111](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/111))

* **Enhancements to existing features**:
    * Add test utilities to support Facilities and Facility List for easy and convenient testing (Pull request [\#98](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/98))
    * Improve feature `split` by accounting for instances when zero members are available or if there is insufficient capacity to accommodate all available members (Pull Request [\#150](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/150))

* **Documentation**:
    * User Guide:
        * Added documentation for the features `deletem`,`setm` and `split` (Pull Request [\#66](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/66))
        * Added documentation for the features `alias`, `unalias` and `aliases` command (Pull Request [\#124](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/124))
        * Did cosmetic and tonal tweaks to make User Guide more welcoming and visually aiding (i.e. screenshots, tips) (Pull Requests [\#135](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/135) [\#163](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/163))
    * Developer Guide:
        * Added implementation details of the `alias`, `unalias` and `aliases` feature as well as UML diagrams for illustration (Pull Request [\#124](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/124))
        * Review overall updates to Developer Guide Design section and update if necessary (Pull Request [\#259](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/259))  

* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#112](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/112), [\#137](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/137)
    * Tested and reported bugs for another team during [PED](https://github.com/YoYoCiti/ped/issues)
