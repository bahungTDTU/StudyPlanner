# Contributing to Study Planner

Thank you for contributing to the Study Planner project! This guide will help you understand how to contribute effectively.

## Team Member Information Template

When adding your information to the PROJECT_REPORT.md, please use this template:

```markdown
### Member [Number]
- **Student ID**: [Your student ID]
- **Full Name**: [Your full name]
- **Email**: [Your email address]
- **GitHub Username**: [Your GitHub username]
- **Assigned Tasks**: 
  - [Describe your main tasks]
  - [List specific features you implemented]
  - [Include bug fixes and improvements]
- **Completion Percentage**: [Your completion percentage]
```

## Git Workflow

### Branch Naming Convention
- Feature branches: `feature/feature-name`
- Bug fixes: `bugfix/issue-description`
- Documentation: `docs/update-description`

### Commit Message Guidelines
- Use present tense ("Add feature" not "Added feature")
- Use imperative mood ("Move cursor to..." not "Moves cursor to...")
- Be descriptive but concise
- Reference issues when applicable (#123)

Example:
```
Add task completion toggle feature

- Implement checkbox in task list
- Update database when task status changes
- Add animation for completion
- Fixes #45
```

### Pull Request Process
1. Create a feature branch from `main`
2. Make your changes
3. Test your changes thoroughly
4. Create a pull request with detailed description
5. Request review from team members
6. Address review comments
7. Merge after approval

## Code Style Guidelines

### Java Code Style
- Follow Android code style conventions
- Use meaningful variable and method names
- Add JavaDoc comments for public methods
- Keep methods focused and small
- Use proper exception handling

Example:
```java
/**
 * Creates a new task in the database
 * @param task The task object to create
 * @return The ID of the newly created task
 */
public long createTask(Task task) {
    // Implementation
}
```

### XML Layout Guidelines
- Use meaningful IDs
- Follow Material Design guidelines
- Keep layouts organized and properly indented
- Use dimension resources instead of hard-coded values
- Use string resources for all text

## Testing Guidelines

### Before Submitting
- [ ] Test on emulator
- [ ] Test basic functionality
- [ ] Check for crashes
- [ ] Verify UI looks correct
- [ ] Test with different data scenarios
- [ ] Check database operations work correctly

### Testing Checklist for Features
- [ ] Feature works as expected
- [ ] Error handling is in place
- [ ] UI updates correctly
- [ ] Data persists correctly
- [ ] No memory leaks
- [ ] Navigation works properly

## Documentation

### When to Update Documentation
- Adding new features
- Changing existing functionality
- Fixing bugs that affect user experience
- Making architectural changes

### What to Document
- New classes and their purpose
- New methods and their parameters
- Complex algorithms or logic
- API changes
- Database schema changes

## Issue Reporting

### Bug Report Template
```markdown
**Bug Description**
[Clear description of the bug]

**Steps to Reproduce**
1. [First step]
2. [Second step]
3. [...]

**Expected Behavior**
[What should happen]

**Actual Behavior**
[What actually happens]

**Screenshots**
[If applicable]

**Environment**
- Android Version: [e.g., 11]
- Device: [e.g., Pixel 4]
- App Version: [e.g., 1.0]
```

### Feature Request Template
```markdown
**Feature Description**
[Clear description of the feature]

**Use Case**
[Why this feature is needed]

**Proposed Solution**
[How you think it should work]

**Alternatives Considered**
[Other approaches you've thought about]
```

## Getting Help

If you need help:
1. Check existing documentation
2. Search closed issues for similar problems
3. Ask in team communication channel
4. Create an issue with the "question" label

## Resources

### Android Development
- [Android Developer Guide](https://developer.android.com)
- [Material Design](https://material.io/design)
- [Android Jetpack](https://developer.android.com/jetpack)

### Version Control
- [Git Documentation](https://git-scm.com/doc)
- [GitHub Guides](https://guides.github.com)

### Tools
- [Android Studio](https://developer.android.com/studio)
- [Gradle](https://gradle.org)

## Project Structure

```
StudyPlanner/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/studyplanner/
│   │   │   │   ├── data/              # Data layer
│   │   │   │   ├── repository/        # Repository pattern
│   │   │   │   ├── ui/                # UI layer
│   │   │   │   └── util/              # Utilities
│   │   │   └── res/                   # Resources
│   │   └── androidTest/               # Tests
│   └── build.gradle.kts
├── docs/                              # Documentation
├── PROJECT_REPORT.md                  # Main project report
└── README.md                          # Project overview
```

## Code Review Checklist

### For Reviewers
- [ ] Code follows style guidelines
- [ ] Changes are well-tested
- [ ] Documentation is updated
- [ ] No unnecessary changes
- [ ] Performance considerations are addressed
- [ ] Security issues are considered
- [ ] Error handling is proper

### For Contributors
- [ ] Self-review completed
- [ ] Tests added/updated
- [ ] Documentation updated
- [ ] Comments added where necessary
- [ ] No debug code left in
- [ ] Build succeeds
- [ ] No warnings introduced

## License

By contributing, you agree that your contributions will be licensed under the same license as the project.

---

**Questions?** Open an issue with the "question" label or contact the team lead.
