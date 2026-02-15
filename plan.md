# Plan: Convert Examples to Standalone Execution

Scope: prioritize the next 5 easiest examples to convert to standalone usage like `examples/simple-validation`.

Assumptions used for ranking:
- Fewer external services required = easier conversion.
- Existing `main` entrypoint = easier.
- Fewer codegen/plugin/runtime classpath quirks = easier.

## Ranked Top 5 (Easiest First)

1. `examples/rest-client`
2. `examples/jsonschema-validation`
3. `examples/a2a-real-world-integration`
4. `examples/protobuf-validation`
5. `examples/simple-protobuf`

## Per-Example Conversion Plan

1. `examples/rest-client`
- Why easy: no Kafka dependency; mostly registry client usage.
- Work:
  - Add `README.md` with prereqs, standalone build command, run commands for each main class.
  - Document expected output and basic artifact verification via `curl`.
  - Add troubleshooting for registry URL/auth mismatch.

2. `examples/jsonschema-validation`
- Why easy: lightweight validation example with a single `main`.
- Work:
  - Add `README.md` with minimal compile/run flow from repo root.
  - Include sample input/output and failure path for invalid payload.
  - Document any required env vars and defaults.

3. `examples/a2a-real-world-integration`
- Why medium-easy: already has `exec-maven-plugin` main class configured.
- Work:
  - Add `README.md` with standalone run command and expected startup logs.
  - Document local port usage and how to verify each mock agent endpoint.
  - Add cleanup/stop guidance.

4. `examples/protobuf-validation`
- Why medium: protobuf toolchain/plugin can be environment-sensitive.
- Work:
  - Add `README.md` with JDK/protobuf prerequisites and standalone commands.
  - Include troubleshooting for `protoc`/OS classifier issues.
  - Add verification steps for successful validation run.

5. `examples/simple-protobuf`
- Why medium: includes Kafka + protobuf codegen, but still single-purpose flow.
- Work:
  - Add `README.md` with required infra (Kafka + Registry) and exact startup order.
  - Provide standalone build/run commands and minimal produce/consume verification.
  - Add troubleshooting for schema registration and broker connectivity.

## Definition of Done (for each conversion)

- `README.md` exists in the example directory.
- README includes: prerequisites, exact standalone commands, expected output, and verification commands.
- Commands run without requiring full examples reactor beyond necessary `-pl ... -am` modules.
- README includes at least 3 troubleshooting bullets for common failures.
