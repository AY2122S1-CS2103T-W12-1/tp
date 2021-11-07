---
layout: page
title: Senthamaraiselvan Shruthi's Project Portfolio Page
---

### Project: SportsPA

SportsPA is a desktop application used to manage memberships and training sessions for NUS sports CCAs. 
The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the ability to mark/unmark members attendance.
    * What it does: allows the user to mark attendance of the members or unmark previously marked attendance.
    * Justification: This feature improves the product significantly because the target users require taking attendance of their members and keeping track of the total attendance and hence this command enables them to do so in a quick and easy manner.
    * Highlights: This enhancement required an analysis of design alternatives regarding how the total attendance and today attendance will be updated and displayed to the user.
    * Pull request: [\#138](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/138)

* **New Feature**: Added the ability to add facilities to SportsPA.
    * What it does: allows the user to add facilities to keep track.
    * Justification: This feature improves the product significantly because the target users require managing training sessions by keeping track of the facilities they have booked so that they can allocate members to these facilities using other commands.
    * Highlights: This enhancement was difficult to implement as there were various classes needed to represent facilities and their attributes, before the command can be implemented.
    * Pull request: [\#96](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/96)

* **New Feature**: Added the skeleton of ability to split members to facilities.
    * What it does: allows the user to add facilities to split members and allocate them to various facilities based on total capacity.
    * Justification: This feature improves the product significantly because the target users can easily allocate their members to different locations while ensuring venue capacity is met.
    * Highlights: This enhancement was challenging to implement and to test if it worked as it required a command to set availability of the members. But at the time this feature was implemented,
      there was no such command implemented yet. So there was a need to create a few dummy availabilities for members in order to test this feature.
    * Pull request: [\#112](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/112)

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/#breakdown=true&search=shruthi0310)

* **Team Tasks**:
    * Morphed the AddressBook to a different product that supports managing of facilities (e.g `addf`)
    * Documented target user profile and some user stories for Developer's Guide
    
* **Enhancement to existing features**:
    * Improved `deletem` command such that it will update the allocations in facilities every time a member is deleted, instead of
    having the users to split again to obtain the updated allocations : [\#216](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/216)

* **Documentation**:
    * User Guide:
        * Added documentation for description of User Guide and the features `addf` and `help` and summary table of commands [\#74](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/74)
        * Added documentation for the features `mark`, `unmark`, `cleara`: [\#138](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/138)
        * Tweaked existing documentation for the features `setm` and `split`: [\#216](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/216)
    * Developer Guide:
        * Added implementation details of the `mark` and `unmark` feature, including UML diagrams. [\#219](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/219)
        * Update Ui section and its diagrams. [\#243](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/243)

* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#121](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/121), [\#124](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/124), 
      [\#144](https://github.com/AY2122S1-CS2103T-W12-1/tp/pull/144)
