# How to Contribute

There are many areas where jMISB could use your help.
To get an idea of some of the things on our road map, see the
[issues board](https://github.com/WestRidgeSystems/jmisb/issues). New contributors
are encouraged to get started on smaller tasks, which we will tag using the
`good first issue` label.

## Development Guidelines

* There are currently two development branches to which all pull requests should
go: `1.x` and `2.x`.

* `1.x` is in maintenance mode, so preferably PRs for new features should go into 
`2.x`.

* If you are fixing a bug, you may submit the PR against either branch. Once
approved on that branch, we will ensure it gets merged into the other branch, 
if appropriate.

* On your branch, please only make changes for the single issue you are
working on. If you find yourself tempted to make other enhancements along the
way, please create a new issue instead.

* Be sure to follow the existing coding style and conventions by looking at
existing code. A full maven build will invoke the formatter, which uses the
AOSP conventions. You can configure your IDE with the same rules - see
<https://github.com/google/google-java-format>.

* A strict requirement is that all code in the `org.jmisb.api` package
be fully documented with javadoc. That includes all classes, interfaces,
methods, parameters, public fields, etc.

* Adding unit tests to cover new functionality is highly recommended, but not
required in all cases. Also, ensure that all existing unit tests pass before
making a pull request.

* Once you have created a pull request, a maintainer will review the changes and
make any suggestions. Once the maintainers are satisfied with the contents of the
pull request, they will merge it into the appropriate branches.

## Other Ways to Support the Project

We would very much appreciate any help in the following areas:

* **Spread the Word**. Please socialize the project with any interested parties.
Growing a community is our #1 priority right now.

* **Test Data**. One of our main challenges is procuring data to adequately
represent what jMISB will encounter in production. If you have any sample data
to share (or a means to simulate it), please let us know!

* **Feature Requests**. Even if you do not have the time to develop a new
feature yourself (or perhaps you are not allowed), feature requests help us to
gauge the requirements of the community.

* **Bug Reports**. If you find a problem, please post add it to the
[issues board](https://github.com/WestRidgeSystems/jmisb/issues).
