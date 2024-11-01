package com.example.todotracker;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TodoUpdater {
    private final Project project;
    private final TodoFinder todoFinder;

    public TodoUpdater(Project project) {
        this.project = project;
        this.todoFinder = new TodoFinder(project);
        registerFileChangeListener();
    }

    private void registerFileChangeListener() {
        VirtualFileManager.getInstance().addVirtualFileListener(new VirtualFileListener() {
            @Override
            public void contentsChanged(@NotNull VirtualFileEvent event) {
                updateTodoList();
            }
        }, project);
    }

    private void updateTodoList() {
        List<String> todos = todoFinder.findTodos();

        ApplicationManager.getApplication().invokeLater(() -> {
            TodoToolWindowFactory.updateTodoList(todos);
        });
    }
}
