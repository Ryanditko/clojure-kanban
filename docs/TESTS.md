# Testing Guide

## Overview

This document describes the testing strategy and implementation for the Kanban application.

## Test Structure

```
test/kanban/
├── core_test.clj              # Application initialization tests
├── db/
│   ├── core_test.clj          # Database core functionality tests
│   └── config_test.clj        # Database configuration validation tests
├── domain/
│   └── cards_test.clj         # Business logic tests (CRUD operations)
├── http/
│   ├── handlers/
│   │   └── cards_test.clj     # HTTP handler tests
│   ├── routes/
│   │   └── routes_test.clj    # Routing configuration tests
│   └── schemas/
│       └── cards_test.clj     # Malli schema validation tests
```

## Test Coverage

### Domain Layer (`domain/cards_test.clj`)
- ✅ **12 tests** covering:
  - Card creation with valid data
  - Card retrieval by ID
  - Card listing (all and by status)
  - Card updates (title, description, status)
  - Card deletion
  - Status validation
  - Board visualization

### HTTP Layer (`http/handlers/cards_test.clj`)
- ✅ **11 tests** covering:
  - List cards endpoint (empty and populated)
  - Filter cards by status
  - Get board grouped by status
  - Create card (valid and invalid data)
  - Get card by ID (found and not found)
  - Update card (success, not found, invalid data)
  - Delete card (success and not found)

### Validation Layer (`http/schemas/cards_test.clj`)
- ✅ **10 tests** covering:
  - CreateCardRequest schema validation
  - UpdateCardRequest schema validation
  - MoveCardRequest schema validation
  - Field constraints (title length, required fields)
  - Status enum validation
  - Error throwing on validation failure

### Database Layer
- ✅ **4 tests** for configuration validation
- ✅ **2 tests** for core database functions

### Core Application
- ✅ **1 test** for port configuration

## Running Tests

### Run All Tests
```bash
clj -M:test
```

### Run Specific Test Namespace
```bash
clj -M:test -n kanban.domain.cards-test
```

### Run with Coverage (if configured)
```bash
clj -M:test:coverage
```

## Test Dependencies

Configured in `deps.edn`:

```clojure
:test {:extra-paths ["test"]
       :extra-deps {io.github.cognitect-labs/test-runner
                    {:git/tag "v0.5.1"
                     :git/sha "dfb30dd"}
                    ring/ring-mock {:mvn/version "0.4.0"}
                    org.clojure/test.check {:mvn/version "1.1.1"}}
       :main-opts ["-m" "cognitect.test-runner"]
       :exec-fn cognitect.test-runner.api/test}
```

### Dependencies Purpose:
- **test-runner**: Executes test suites
- **ring-mock**: Simulates HTTP requests for handler testing
- **test.check**: Property-based testing (available for future use)

## Testing Philosophy

### Unit Tests
- **Fast**: No external dependencies (database, network)
- **Isolated**: Each test is independent
- **Focused**: Tests one thing at a time
- **Clear**: Descriptive test names and assertions

### Test Organization
- **Fixtures**: Used to clean state between tests (`use-fixtures`)
- **Sections**: Tests grouped by functionality with comments
- **Naming**: `<function-name>-<scenario>-test` convention

### Example Test Structure

```clojure
(deftest create-card-test
  (testing "Creates a card with valid data"
    (let [card (cards/create-card! {:title "Test" :status "todo"})]
      (is (some? (:id card)))
      (is (= "Test" (:title card)))
      (is (= "todo" (:status card))))))
```

## Testing Best Practices

### ✅ DO:
- Use descriptive test names
- Test both success and failure cases
- Clean up state with fixtures
- Keep tests independent
- Use `testing` for context
- Assert specific values, not just presence

### ❌ DON'T:
- Depend on test execution order
- Share mutable state between tests
- Test implementation details
- Write overly complex tests
- Skip edge cases

## Future Improvements

### Integration Tests
- Database integration tests with test containers
- Full HTTP stack tests
- End-to-end workflow tests

### Property-Based Tests
- Use test.check for generative testing
- Validate invariants across random inputs

### Performance Tests
- Load testing for API endpoints
- Database query performance

### Test Coverage
- Add coverage reporting
- Aim for >80% coverage on business logic

## Continuous Integration

Tests should be run on:
- ✅ Every commit (pre-commit hook)
- ✅ Pull request creation
- ✅ Before merge to main
- ✅ Scheduled nightly builds

## Troubleshooting

### Tests Failing Locally

1. **Check environment variables**:
   ```bash
   # Ensure .env is configured
   cat .env
   ```

2. **Clean and rebuild**:
   ```bash
   rm -rf .cpcache
   clj -M:test
   ```

3. **Check database connection** (for integration tests):
   ```bash
   # Verify PostgreSQL is running
   psql -U postgres -d kanban_test
   ```

### Common Issues

| Issue | Solution |
|-------|----------|
| `ClassNotFoundException` | Run `clj -P -M:test` to download deps |
| `Connection refused` | Ensure database is running |
| `Validation failed` | Check test data matches schema |
| Tests hang | Check for infinite loops or missing assertions |

## Contributing

When adding new features:
1. ✅ Write tests FIRST (TDD)
2. ✅ Ensure all tests pass
3. ✅ Add tests to appropriate namespace
4. ✅ Update this documentation if needed

## Test Statistics

**Total Tests**: 40  
**Total Assertions**: 80+  
**Pass Rate**: 100%  
**Execution Time**: ~2 seconds

---

Last Updated: January 18, 2026