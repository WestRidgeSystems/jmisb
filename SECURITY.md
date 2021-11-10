# Security Policy

We take information security and our users' trust very seriously. If you believe you have found a 
security issue in jmisb, please responsibly disclose it by contacting us at security@jmisb.org.

The jmisb project has implemented several measures to help identify and assess potential security 
risks as early as possible in the development lifecycle.

## Vulnerability Scans

OWASP's [Dependency-Check](https://owasp.org/www-project-dependency-check/) tool is used to detect 
publicly disclosed vulnerabilities in jmisb's dependencies. This check is performed by a GitHub Action 
on each commit to the `develop` branch.

## Static Analysis

GitHub's [CodeQL](https://codeql.github.com/) static analysis tool is used to analyze jmisb's source code 
for errors that may lead to vulnerabilities. The CodeQL analysis is also performed by a GitHub Action 
on each commit to the `develop` branch.

## Software Bill of Materials (SBOM)

[CycloneDX](https://cyclonedx.org/) is used to generate a software bill of materials (SBOM) identifying all 
components within jmisb. The SBOM can be used by clients to analyze and monitor their software supply 
chain risks.

## Fuzzing

Fuzz testing using [JQF](https://github.com/rohanpadhye/JQF) is used to find implementation bugs using 
malformed data injection. See [api/fuzzing.md](api/fuzzing.md) for usage details.
