package com.example.application.views.editora;

import com.example.application.controller.ControllerEditora;
import com.example.application.model.Autor;
import com.example.application.model.Editora;
import com.example.application.services.SamplePersonService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Editora")
@Route(value = "editora", layout = MainLayout.class)
@Uses(Icon.class)
public class EditoraView extends Composite<VerticalLayout> {
    ControllerEditora controller = new ControllerEditora();
    VerticalLayout layoutColumn2 = new VerticalLayout();
    H3 h3 = new H3();
    VerticalLayout layoutColumn3 = new VerticalLayout();
    Hr hr = new Hr();
    FormLayout formLayout2Col = new FormLayout();
    TextField textField = new TextField();
    HorizontalLayout layoutRow = new HorizontalLayout();
    Button buttonPrimary = new Button();
    Hr hr2 = new Hr();
    HorizontalLayout layoutRow2 = new HorizontalLayout();
    TextField textField2 = new TextField();
    Button buttonSecondary2 = new Button();
    Grid<Editora> grid;

    public EditoraView() {
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutColumn2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        h3.setText("Cadastrar Editora");
        h3.setWidth("max-content");
        layoutColumn3.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.setWidth("100%");
        layoutColumn3.getStyle().set("flex-grow", "1");
        formLayout2Col.setWidth("100%");
        textField.setLabel("Editora");
        textField.setPlaceholder("Insira o nome da Editora");
        textField.setWidth("min-content");
        layoutRow.setWidthFull();
        layoutColumn3.setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("50px");
        layoutRow.setAlignItems(Alignment.CENTER);
        layoutRow.setJustifyContentMode(JustifyContentMode.START);
        buttonPrimary.setText("Salvar");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary.addClickListener(event -> {
            Editora editora = new Editora();
            editora.setNome_editora(textField.getValue());
            if (controller.inserir(editora) == true) {
                Notification notification = new Notification(
                        "Editora salvo com sucesso.", 3000);
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.setPosition(Notification.Position.MIDDLE);
                notification.open();
            } else {
                Notification notification = new Notification(
                        "Erro ao salvar. Verifique se todos os dados foram preenchidos.", 3000);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setPosition(Notification.Position.MIDDLE);
                notification.open();
            }
        });

        layoutRow2.setWidthFull();
        layoutColumn3.setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.setHeight("50px");
        layoutRow2.setAlignItems(Alignment.END);
        layoutRow2.setJustifyContentMode(JustifyContentMode.END);
        textField2.setPlaceholder("Insira um ID");
        textField2.setPrefixComponent(new Icon("lumo", "search"));
        textField2.setWidth("min-content");
        buttonSecondary2.setText("Pesquisar");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.END, buttonSecondary2);
        buttonSecondary2.setWidth("min-content");

       buttonSecondary2.addClickListener(event -> {
            if (textField2.isEmpty()) {
                List<Editora> editoras = controller.pesquisarTodos();
                addGridToConsultaTab(editoras);
            }else{
                try{
                    int id = (int) Math.round(Double.parseDouble(textField2.getValue()));
                    Editora editora = controller.pesquisar(id);
                    if (editora != null) {
                        List<Editora> editoraEncontrados = new ArrayList<>();
                        editoraEncontrados.add(editora);
                        addGridToConsultaTab(editoraEncontrados);
                    } else {
                        Notification notification = new Notification(
                                "Editora com o ID fornecido não encontrado.", 3000);
                        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                        notification.setPosition(Notification.Position.MIDDLE);
                        notification.open();
                    }
                }catch (NumberFormatException e) {
                    Notification notification = new Notification(
                            "ID inválido. Por favor, insira um ID válido.", 3000);
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    notification.setPosition(Notification.Position.MIDDLE);
                    notification.open();
                }
            }
        });
        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(layoutColumn3);
        layoutColumn3.add(hr);
        layoutColumn3.add(formLayout2Col);
        formLayout2Col.add(textField);
        layoutColumn3.add(layoutRow);
        layoutRow.add(buttonPrimary);
        layoutColumn3.add(hr2);
        layoutColumn3.add(layoutRow2);
        layoutRow2.add(textField2);
        layoutRow2.add(buttonSecondary2);
    }

    private void addGridToConsultaTab(List<Editora> editoras) {
        if (grid == null) {
            grid = new Grid<>();
            grid.addColumn(Editora::getId).setHeader("ID");
            grid.addColumn(Editora::getNome_editora).setHeader("Nome");
    
            grid.addComponentColumn(editora -> {

                MenuBar menuBar = new MenuBar();
                
                MenuItem editarItem = menuBar.addItem(new Icon(VaadinIcon.EDIT), e -> abrirPopupEdicao(editora));
                MenuItem excluirItem = menuBar.addItem(new Icon(VaadinIcon.TRASH), e -> abrirPopupExclusao(editora));
                
                editarItem.getElement().setAttribute("title", "Editar editora");
                excluirItem.getElement().setAttribute("title", "Excluir editora");
                
                return menuBar;
            }).setHeader("Opções");
            
            grid.setItems(editoras);
            layoutColumn3.add(grid);
        } else {
            grid.setItems(editoras);
        }
    }

    private void abrirPopupEdicao(Editora editora) {
        Dialog dialog = new Dialog();
        FormLayout formLayout = new FormLayout();
        TextField nomeField = new TextField("Nome");
        nomeField.setValue(editora.getNome_editora());
        formLayout.add(nomeField);

        Button confirmarButton = new Button("Confirmar", event -> {
            editora.setNome_editora(nomeField.getValue());
            if (controller.alterar(editora)) {
                Notification.show("Editora alterada com sucesso.").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                addGridToConsultaTab(controller.pesquisarTodos());
            } else {
                Notification.show("Erro ao alterar. Verifique se todos os dados foram preenchidos.")
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
            dialog.close();
        });
        Button cancelarButton = new Button("Cancelar", event -> dialog.close());

        formLayout.add(confirmarButton, cancelarButton);
        dialog.add(formLayout);
        dialog.open();
    }

    private void adicionarIconeEdicao(Grid<Autor> grid, Editora editora) {
        Icon iconEditar = new Icon(VaadinIcon.EDIT);
        iconEditar.addClickListener(event -> abrirPopupEdicao(editora));

        grid.addComponentColumn(item -> {
            MenuBar menuBar = new MenuBar();
            MenuItem editarItem = menuBar.addItem("Editar", e -> abrirPopupEdicao(editora));
            editarItem.getElement().setAttribute("title", "Editar editora");
            return menuBar;
        }).setHeader("Opções");
    }

    private void abrirPopupExclusao(Editora editora) {
        Dialog dialog = new Dialog();
        FormLayout formLayout = new FormLayout();

        Span mensagem = new Span("Tem certeza que deseja excluir?");
        formLayout.add(mensagem);

        NumberField idField = new NumberField("ID");
        idField.setValue((double) editora.getId());
        idField.setReadOnly(true);
        formLayout.add(idField);

        int id = (int) Math.round(idField.getValue());
        final Editora editoraParaExcluir = controller.pesquisar(id);

        Button confirmarButton = new Button("Confirmar", event -> {
            if (controller.excluir(editoraParaExcluir)) {
                Notification.show("Editora excluida com sucesso.").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                addGridToConsultaTab(controller.pesquisarTodos());
            } else {
                Notification.show("Erro ao alterar. Verifique se todos os dados foram preenchidos.")
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
            dialog.close();
        });
        Button cancelarButton = new Button("Cancelar", event -> dialog.close());

        formLayout.add(confirmarButton, cancelarButton);
        dialog.add(formLayout);
        dialog.open();
    }

    private void adicionarIconeExclusao(Grid<Autor> grid, Editora editora) {
        Icon iconEditar = new Icon(VaadinIcon.EDIT);
        iconEditar.addClickListener(event -> abrirPopupExclusao(editora));

        grid.addComponentColumn(item -> {
            MenuBar menuBar = new MenuBar();
            MenuItem editarItem = menuBar.addItem("Exclusao", e -> abrirPopupExclusao(editora));
            editarItem.getElement().setAttribute("title", "Excluir autor");
            return menuBar;
        }).setHeader("Opções");
    }
}
