## What is Version Control?

A Version Control System (VCS) is software that tracks and manages changes to files over time. It allows you to keep a history of every modification made to your code, documents, or any other set of files.

**Why is it needed?**

Imagine working on a project without version control:

1. **The "Final" File Problem:** You might end up with files named `report.doc`, `report_final.doc`, `report_final_v2.doc`, `report_FINAL_FOR_REAL.doc`. It becomes impossible to track which is actually the most up-to-date or what changed between them.
2. **Fear of Breaking Things:** If you want to try a new, experimental feature, you might hesitate because if it breaks the existing code, it's hard to go back to exactly how things were.
3. **Collaboration Chaos:** If you and a teammate are editing the same file at the same time and try to save it, one person's work will overwrite the other's.

**How Version Control Solves This (The Benefits):**

- **Complete History:** It saves a snapshot of your project every time you make a "commit." You can look back in time to see exactly what changed, who changed it, and why (via the commit message).
- **Time Travel:** If a new update breaks your project, you can instantly revert your entire codebase back to a previous, working state.
- **Safe Experimentation (Branching):** You can create a "branch" (a parallel universe of your project). You can build a crazy new feature on that branch without affecting the main working code. If it works, you merge it in; if it fails, you delete the branch and nothing is lost.
- **Seamless Collaboration:** Multiple developers can work on the same project—even the same files—simultaneously. The VCS tracks everyone's independent changes and helps merge them together intelligently, flagging conflicts if two people changed the exact same line of code.
- **Backup and Restore:** Because your code history is typically pushed to a remote server (like GitHub), if your local computer crashes, you lose no work. You just download the repository onto a new machine.

## What is Git?

Git is currently the most popular version control system in the world. It was created in 2005 by Linus Torvalds (the creator of Linux) to manage the massive, highly collaborative Linux kernel project.

**What makes Git special?**

Older version control systems (like SVN) were *centralized*. You had to be connected to a central server to see the history or commit changes.

Git is a **Distributed Version Control System (DVCS)**.

When you use Git, you don't just check out the latest snapshot of the files; you clone the *entire repository*. Every developer's local computer contains a full, independent copy of the project and its entire history.

**This means:**

- You can work completely offline. You can branch, commit, and view history while on an airplane.
- It's incredibly fast because most operations are local.
- There's no single point of failure. If the main server goes down, any developer's local copy can be used to restore it.

## The Git Ecosystem: Git vs. GitHub

It is very important to understand the distinction between the software and the hosting platforms:

- **Git** is the actual software/engine running locally on your computer that tracks the versions.
- **GitHub** (and alternatives like GitLab or Bitbucket) is a website that hosts Git repositories on the internet. It provides a visual interface, collaboration tools (like Pull Requests for code review), and a central hub for teams to push and pull their code.

You don't need GitHub to use Git, but you almost always use them together when working with others.

## Core Concepts

Before memorizing commands, you need to understand Git's "three-tree architecture." Files in your project go through three main states before they are shared with others:

1. **Working Directory:** Your actual files as they currently exist on your computer.
2. **Staging Area (Index):** A rough draft space. You place files here when you are getting ready to commit them.
3. **Local Repository:** Where Git permanently stores the snapshots (commits) of your project on your local machine.
4. **Remote Repository:** A version of your project hosted on the internet (e.g., GitHub, GitLab, Bitbucket).

## Setup and Initialization

These commands are used to configure your Git environment and start tracking a project.

- **`git config --global user.name "Your Name"`** Sets the name attached to your commits.
- **`git config --global user.email "you@example.com"`** Sets the email attached to your commits.
- **`git init`** Turns an existing directory into a brand new Git repository.
- **`git clone [url]`** Downloads an entire repository from a remote server to your local machine.

### 1. How Git Stores Your Work (The Main Areas)

A file moves through these places:

1. **Working Directory.** Your files as they are on your computer right now.
2. **Staging Area (Index).** A waiting area for the changes you plan to commit.
3. **Local Repository.** Your saved commits on your own machine.
4. **Remote Repository.** The shared copy on GitHub.

Flow: edit file, then `git add` (staging), then `git commit` (local repo), then `git push` (remote).

### 2. Setup Commands

- `git config --global user.name "Your Name"` sets your name for commits.
- `git config --global user.email "you@example.com"` sets your email for commits.
- `git init` turns a folder into a new Git repository.
- `git clone [url]` downloads a full repository from the remote.

### 3. Daily Workflow Commands

- `git status` shows your branch, changed files, and staged files. Use it often.
- `git diff` shows exact line changes that are not staged yet.
- `git diff --staged` shows what you have staged.
- `git add [file]` stages one file.
- `git add -p` stages changes piece by piece, so you can review each one.
- `git add .` stages everything. Use it carefully.
- `git commit -m "message"` saves the staged changes as a commit.
- `git commit -am "message"` stages tracked files and commits. It skips new files.

### 4. Branching and Merging Commands

- `git branch` lists local branches. The current one has a `*`.
- `git branch [name]` creates a branch but does not switch to it.
- `git switch [name]` (or `git checkout [name]`) moves to a branch.
- `git switch -c [name]` (or `git checkout -b [name]`) creates a branch and switches to it.
- `git merge [name]` merges that branch into the branch you are on now.
- `git branch -d [name]` deletes a merged branch.

### 5. Remote Commands

- `git remote -v` shows the remote URLs.
- `git push origin [branch]` uploads your commits to that branch on GitHub.
- `git fetch` downloads new data but does not change your files. It is safe.
- `git pull` is fetch plus merge.
- `git pull --rebase` is fetch plus rebase. It keeps history in a straight line.

### 6. History and Undo Commands

- `git log` shows commit history (press `q` to exit).
- `git log --oneline` shows a short version.
- `git restore [file]` throws away uncommitted changes in a file.
- `git revert [hash]` creates a new commit that undoes an old commit. This is the safest way to undo shared work.
- `git reset [hash]` moves your branch back to an older commit. Use it only on your own local work that is not pushed.
- `git stash` saves your unfinished changes aside. `git stash pop` brings them back.

### 7. What Happens When Two Developers Push

**Scenario:** Dev 1 and Dev 2 both have the same main branch. Dev 1 pushes first. Dev 2 pushes later.

- Dev 1’s changes are **not lost**.
- Git rejects Dev 2’s push with an error like “updates were rejected because the remote contains work that you do not have locally.”
- Dev 2 must first run `git pull` (or `git pull --rebase`), fix conflicts if any, test, and then push.
- The only way to lose work is `git push --force`. Do not use it on shared branches.

**Scenario with branches:** Dev 1 works on `feature-a` and merges it into main. Dev 2 works on `experiment-b`. Main has Dev 1’s work. Dev 2’s work stays safe on his branch. When ready, Dev 2 pulls the latest main into his branch, fixes conflicts, and opens a Pull Request.

### 8. Teamwork Tips (Communication)

1. **Tell your team what you are working on.** Use a chat group, stand-up, or task board. This stops two people from editing the same file.
2. **Split work by area or file when possible.** Fewer overlaps means fewer conflicts.
3. **Announce big changes early.** Renaming files, moving folders, or reformatting the whole code will cause conflicts for everyone. Tell the team first.
4. **Do not mix formatting changes with logic changes.** Keep them in separate commits.
5. **Pull often.** Pull at the start of the day and before you start a new task.
6. **Push small and often.** Big, late merges create big conflicts.
7. **Use clear PR descriptions.** Say what changed, why, and how to test it.
8. **Review each other’s code.** Give comments politely and be specific.
9. **If a conflict is in someone else’s code, ask them.** Do not guess what they meant.
10. **Tell the team when you break main.** Fix it fast or revert the commit.

### 9. How to Resolve a Merge Conflict (Step by Step)

A conflict happens when two people change the **same lines** of the same file.

1. Run `git pull` (or merge). Git says: “CONFLICT in [file]”.
2. Run `git status` to see which files are in conflict.
3. Open the file. You will see markers like this:

```
<<<<<<< HEAD
your version of the lines
=======
the other person's version of the lines
>>>>>>> origin/main
```

1. Decide the final code. Keep yours, keep theirs, or combine both.
2. **Delete the markers** (`<<<<<<<`, `=======`, `>>>>>>>`).
3. Save the file.
4. Run the code and tests to confirm it still works.
5. Run `git add [file]` to mark it as resolved.
6. Run `git commit` (for a merge) or `git rebase --continue` (for a rebase).
7. Run `git push`.

**Tips for conflicts**

- Do not panic. Nothing is lost.
- Talk to the other developer if you are not sure what their change does.
- Never pick one side blindly. Read both.
- If things go wrong, `git merge --abort` or `git rebase --abort` takes you back to before the merge.
- Many editors (VS Code, IntelliJ) have a visual conflict tool. Use it.

### 10. Practical Tips

**Commits**

- Make small commits. One logical change per commit.
- Write clear messages. Good: “Fix null error in login validation”. Bad: “changes”, “fix”, “asdf”.
- Do not commit broken code.

**Branches**

- Do not work directly on main. Create a branch for every task.
- Use clear branch names: `feature/login-page`, `bugfix/null-error`, `hotfix/payment-crash`.
- Delete branches after they are merged.
- Keep branches short-lived. Long-lived branches cause painful merges.

**Safety**

- Use `.gitignore` for build files, `node_modules`, `.env`, IDE folders, logs.
- Never commit passwords, API keys, or tokens. They stay in history even after you delete them.
- Never use `git push --force` on shared branches. If you must, use `--force-with-lease` and tell the team first.
- Do not use `git reset --hard` unless you are sure. It deletes uncommitted work.
- Turn on **branch protection** on GitHub: require PR review, require passing checks, block force push.

**Habits**

- Run `git status` before every command you are unsure about.
- Use `git fetch` first if you want to see what changed without touching your files.
- Commit before you switch branches, or use `git stash`.

### 11. Checklist: Before You Commit

-  I ran `git status` and `git diff`. Only intended changes are there.
-  I staged only the files I want (`git add <file>` or `git add -p`).
-  No secrets, passwords, or junk files are included.
-  The code builds and runs on my machine.
-  Tests pass and the linter is clean.
-  My commit does one thing only.
-  My commit message is clear.

### 12. Checklist: Before You Push

-  I am on the right branch (`git branch`).
-  I pulled the latest changes (`git pull --rebase origin main` or `git pull`).
-  I resolved all conflicts and removed all conflict markers.
-  I re-ran the build and tests after the pull.
-  I checked `git log --oneline -5` and `git diff origin/main`.
-  I am not force pushing.
-  For a team project, I am pushing a branch and will open a PR, not pushing straight to main.

### 13. Recommended Team Workflow (Start to Finish)

1. `git switch main` then `git pull` to get the latest code.
2. `git switch -c feature/my-task` to create your branch.
3. Make changes. Commit small and often.
4. Regularly bring latest main into your branch (`git pull origin main`).
5. Resolve conflicts if any. Tell teammates if you need help.
6. Run tests locally.
7. `git push origin feature/my-task`.
8. Open a Pull Request on GitHub with a clear description.
9. Teammates review. CI checks run. Fix review comments.
10. Merge the PR into main.
11. Delete the branch. Pull main again locally.

### 14. Common Mistakes and Fixes

- **Committed to the wrong branch.** Create the right branch from here (`git switch -c correct-branch`), then fix the old branch. Ask for help if the commit was pushed.
- **Wrong commit message (not pushed yet).** `git commit --amend -m "new message"`.
- **Forgot a file in the last commit (not pushed yet).** `git add file` then `git commit --amend --no-edit`.
- **Want to undo a pushed commit.** Use `git revert [hash]`, not reset.
- **Committed a secret.** Change the secret immediately (rotate it). Deleting the file is not enough.
- **Push rejected.** Pull first, resolve conflicts, then push.
- **Lost commits after a reset.** `git reflog` shows recent positions, and you can often recover from it.

### 15. Quick Cheat Sheet

```
git status                 what is going on
git pull --rebase          get latest changes
git switch -c feature/x    new branch
git add -p                 stage carefully
git commit -m "message"    save snapshot
git push origin feature/x  upload branch
git fetch                  check remote safely
git stash / git stash pop  park and restore work
git revert <hash>          safely undo a pushed commit
```