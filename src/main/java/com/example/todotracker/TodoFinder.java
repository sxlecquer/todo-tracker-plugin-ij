package com.example.todotracker;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;
import com.intellij.openapi.fileTypes.StdFileTypes;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TodoFinder {
    private final Project project;

    public TodoFinder(Project project) {
        this.project = project;
    }

    public List<String> findTodos() {
        List<String> todos = new ArrayList<>();

        FileBasedIndex.getInstance().processValues(FileTypeIndex.NAME, StdFileTypes.JAVA, null,
                (file, value) -> {
                    PsiFile psiFile = PsiManager.getInstance(project).findFile(file);
                    if(psiFile != null) {
                        psiFile.accept(new PsiRecursiveElementWalkingVisitor() {
                            @Override
                            public void visitElement(@NotNull PsiElement element) {
                                if(element instanceof PsiComment) {
                                    String commentText = element.getText();
                                    if(commentText.contains("TODO")) {
                                        todos.add(commentText.trim());
                                    }
                                }
                                super.visitElement(element);
                            }
                        });
                    }
                    return true;
                }, GlobalSearchScope.projectScope(project));

        return todos;
    }
}
