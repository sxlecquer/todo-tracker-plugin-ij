package com.example.todotracker;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TodoToolWindowFactory implements ToolWindowFactory {
    private static final DefaultListModel<String> LIST_MODEL = new DefaultListModel<>();
    private static JList<String> todoListUI = new JList<>(LIST_MODEL);

    public void createToolWindowContent(@NotNull Project project, ToolWindow toolWindow) {
        todoListUI = new JList<>(LIST_MODEL);

        JButton completeButton = new JButton("Mark as Complete");
        completeButton.addActionListener(e -> {
            int selectedIndex = todoListUI.getSelectedIndex();
            if (selectedIndex != -1) LIST_MODEL.remove(selectedIndex);
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(todoListUI), BorderLayout.CENTER);
        panel.add(completeButton, BorderLayout.SOUTH);

        toolWindow.getComponent().add(panel);

        new TodoUpdater(project);
    }

    public static void updateTodoList(List<String> todos) {
        LIST_MODEL.clear();
        todos.forEach(LIST_MODEL::addElement);
    }
}
