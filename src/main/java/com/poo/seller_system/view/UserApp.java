/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.poo.seller_system.view;

import com.poo.seller_system.Seller_system;
import com.poo.seller_system.persistence.model.Category;
import com.poo.seller_system.persistence.model.Client;
import com.poo.seller_system.persistence.model.DocumentType;
import com.poo.seller_system.persistence.model.OrderDetail;
import com.poo.seller_system.persistence.model.Orders;
import com.poo.seller_system.persistence.model.Product;
import com.poo.seller_system.persistence.model.ProductDetail;
import com.poo.seller_system.persistence.model.Provider;
import com.poo.seller_system.persistence.model.Role;
import com.poo.seller_system.persistence.model.SaleDetail;
import com.poo.seller_system.persistence.model.Sales;
import com.poo.seller_system.persistence.model.User;
import com.poo.seller_system.service.CategoryService;
import com.poo.seller_system.service.ClientService;
import com.poo.seller_system.service.OrderService;
import com.poo.seller_system.service.ProductService;
import com.poo.seller_system.service.ProviderService;
import com.poo.seller_system.service.RoleService;
import com.poo.seller_system.service.SaleService;
import com.poo.seller_system.service.UserService;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.RowFilter;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Jesus
 */
public class UserApp extends javax.swing.JFrame {
    
    private UserService userService = UserService.getInstance();
    private CategoryService categoryService = CategoryService.getInstance();
    private ProductService productService = ProductService.getInstance();
    private ClientService clientService = ClientService.getInstance();
    private ProviderService providerService = ProviderService.getInstance();
    private SaleService saleService = SaleService.getInstance();
    private OrderService orderService = OrderService.getInstance();
    private Long selectedProvider = 0L;
    private Long selectedUser = 0L;
    private Long selectedCategory = 0L;
    private Long selectedProduct = 0L;
    private Long selectedProductDetail = 0L;
    private Long selectedClient = 0L;
    private String selectedProductSaleTable = "";
    private String selectedProductOrderTable = "";
    
    
    
    List<Role> roles;
    List<Category> categories;
    List<Client> clients;
    
    
    List<Product> productsListTableSale;
    List<Product> productsListTableOrder;
    List<Client> clientListTableSale;
    List<Provider> providerListTableBuy;
    
    Set<SaleDetail> productsToSell = new HashSet<>();
    Set<OrderDetail> productsToBuy = new HashSet<>();
    private Client selectedClientToSale;
    private Product selectedProductToSale;
    private Product selectedProductToBuy;
    private Provider selectedProviderToBuy;
    
    // Flags filter
    private boolean productSaleFlag = false;
    private boolean clientSaleFlag = false;
    private boolean productOrderFlag = false;
    private boolean providerOrderFlag = false;
    private boolean categoryFlag = false;
    private boolean productFlag = false;
    private boolean reportSaleFlag = false;
    private boolean reportOrderFlag = false;
      
    

    /**
     * Creates new form MainApp
     */
    public UserApp() {
        initComponents();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - 1100) / 2;
        int y = (screenSize.height - 810) / 2;           

        this.setLocation(x, y);
        
        currentUsername.setText(Seller_system.currentUser.getName());
        
        // Mantainer submenu
        mantainerCategory = new JMenuItem("Categorias");
        mantainerProducts = new JMenuItem("Productos");        
        mantainerBussiness = new JMenuItem("Negocio");
        mantainerSubmenu.add(mantainerCategory);
        mantainerSubmenu.add(mantainerProducts); 
        mantainerSubmenu.add(mantainerBussiness);        
      
        // Mantainer Action Listener
        mantainerCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryButtonActionPerformed(evt);
            }
        });
        
        mantainerProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productsButtonActionPerformed(evt);
            }
        });
        
        mantainerBussiness.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bussinessButtonActionPerformed(evt);
            }
        });
        
        // Seller submenu
        sellerRegister = new JMenuItem("Registrar");
        sellerDetails = new JMenuItem("Detalles");        
        sellerSubmenu.add(sellerRegister);
        sellerSubmenu.add(sellerDetails); 
        
        sellerRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sellerRegisterButtonActionPerformed(evt);
            }
        });
        
        sellerDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sellerDetailsButtonActionPerformed(evt);
            }
        });  
        
        // Shop submenu
        shopRegister = new JMenuItem("Registrar");
        shopDetails = new JMenuItem("Detalles");        
        shopSubmenu.add(shopRegister);
        shopSubmenu.add(shopDetails); 
        
        shopRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shopRegisterButtonActionPerformed(evt);
            }
        });
        
        shopDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shopDetailsButtonActionPerformed(evt);
            }
        });
        
        // Report submenu
        reportSeller = new JMenuItem("Report compras");
        reportShop = new JMenuItem("Reporte ventas");
        reportSubmenu.add(reportSeller);
        reportSubmenu.add(reportShop);
        
        reportSeller.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportSellerButtonActionPerformed(evt);
            }
        });
        
        reportShop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportShopButtonActionPerformed(evt);
            }
        });
        
        jTabbedPane1.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI(){
            @Override
            protected int calculateTabAreaHeight(int tabPlacement, int runCount, int maxTabHeight){
                return 0;
            }
        });
        
        // Fill CB roles
        roles = RoleService.getInstance().getAllRoles();
        
        roles.stream()
                .filter(r -> !"SUPERADMIN".equals(r.getName()))
                .forEach( r -> userRoleCB.addItem(r.getName()));
        
        
        
        // Fill CB Status
        userStatusCB.addItem("Activo");
        userStatusCB.addItem("Inactivo");
        categoryStatusCB.addItem("Activo");
        categoryStatusCB.addItem("Inactivo");
        statusProductCB.addItem("Activo");
        statusProductCB.addItem("Inactivo");    
        statusClientCB.addItem("Activo");
        statusClientCB.addItem("Inactivo"); 
        statusProviderCB.addItem("Activo");
        statusProviderCB.addItem("Inactivo"); 
        docTypeSaleFieldCB.addItem("Boleta");
        docTypeSaleFieldCB.addItem("Factura");
        docTypeBuyFieldCB.addItem("Boleta");
        docTypeBuyFieldCB.addItem("Factura");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaActual = LocalDate.now().format(formatter);
        dateSaleField.setText(fechaActual);
        dateBuyField.setText(fechaActual);
        
        
        // Fill CB         
        fillUserTable();
        fillProductTable();        
        fillCategoryTable();
        fillCategoryProductCB();
        fillClientTable();
        fillProviderTable();
        fillSaleTable();
        fillBuyTable();

        // BorderTitle
        sellInfoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Informacion de la venta"));
        sellProviderPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Informacion del Cliente"));
        sellProductPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Informacion del producto"));
        
        buyInfoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Informacion de la compra"));
        buyProviderPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Informacion del Proveedor"));
        buyProductPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Informacion del producto"));
        
        infoVentaPanel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Informacion de la venta"));
        infoClientePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Informacion del cliente"));
        
        // Field Sell setting
        
        calculateDiscountPrice();
        calculateChange();
        subtotalBuyCalculate();
        codeSaleFieldConstraint();
        
        // random things
        quantitySaleSpinner.setSize(20, 10);
        modelSaleDetailsTable();
        modelOrderDetailsTable();
        
        fillReportSaleTable();
        fillReportOrderTable();
        
        jTabbedPane1.setSelectedIndex(4);
    }
    // UserFilter
    
    private void userFilter(){
        DefaultTableModel model = (DefaultTableModel) userTable.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        userTable.setRowSorter(sorter);
        
        String[] columnNames = {"ID","Nr. Documento", "Nombre", "Correo", "Rol", "Estado"};
        userFilterCB.removeAllItems();
        for(String col : columnNames){
            userFilterCB.addItem(col);
        }
        
        userFilterField.getDocument().addDocumentListener(new DocumentListener(){
            public void insertUpdate(DocumentEvent e){
                filtrar();
            }
            public void removeUpdate(DocumentEvent e){
                filtrar();
            }
            public void changedUpdate(DocumentEvent e){
                filtrar();
            }
            
            private void filtrar(){
                int columnIndex = userFilterCB.getSelectedIndex();
                String text = userFilterField.getText();
                
                if(text.trim().length() == 0){
                    sorter.setRowFilter(null);
                } else{
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)^" + Pattern.quote(text), columnIndex));
                }
            }
         
        });
    }
    
    // Category Filter
    
    private void categoryFilter(){
        DefaultTableModel model = (DefaultTableModel) categoryTable.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        categoryTable.setRowSorter(sorter);
        
        String[] columnNames = {"ID","Descripcion", "Estado"};
        categoryFilterCB.removeAllItems();
        for(String col : columnNames){
            categoryFilterCB.addItem(col);
        }
        
        categoryFilterField.getDocument().addDocumentListener(new DocumentListener(){
            public void insertUpdate(DocumentEvent e){
                filtrar();
            }
            public void removeUpdate(DocumentEvent e){
                filtrar();
            }
            public void changedUpdate(DocumentEvent e){
                filtrar();
            }
            
            private void filtrar(){
                int columnIndex = categoryFilterCB.getSelectedIndex();
                String text = categoryFilterField.getText();
                
                if(text.trim().length() == 0){
                    sorter.setRowFilter(null);
                } else{
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)^" + Pattern.quote(text), columnIndex));
                }
            }
         
        });
    }
    
    // Product Filter
    private void productFilter(){
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        productTable.setRowSorter(sorter);
        
        String[] columnNames = {"ID","Codigo", "Nombre", "Descripcion", "Categoria", "Stock","Precio lista","Estado"};
        productFilterCB.removeAllItems();
        for(String col : columnNames){
            productFilterCB.addItem(col);
        }
        
        productFilterField.getDocument().addDocumentListener(new DocumentListener(){
            public void insertUpdate(DocumentEvent e){
                filtrar();
            }
            public void removeUpdate(DocumentEvent e){
                filtrar();
            }
            public void changedUpdate(DocumentEvent e){
                filtrar();
            }
            
            private void filtrar(){
                int columnIndex = productFilterCB.getSelectedIndex();
                String text = productFilterField.getText();
                
                if(text.trim().length() == 0){
                    sorter.setRowFilter(null);
                } else{
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)^" + Pattern.quote(text), columnIndex));
                }
            }
         
        });
    }
    
    private void openFilter(javax.swing.JTable table, String[] headers, javax.swing.JComboBox<String> comboBox, javax.swing.JTextField textField){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        
        String[] columnNames = headers;
        comboBox.removeAllItems();
        for(String col : columnNames){
            comboBox.addItem(col);
        }
        
        textField.getDocument().addDocumentListener(new DocumentListener(){
            public void insertUpdate(DocumentEvent e){
                filtrar();
            }
            public void removeUpdate(DocumentEvent e){
                filtrar();
            }
            public void changedUpdate(DocumentEvent e){
                filtrar();
            }
            
            private void filtrar(){
                int columnIndex = comboBox.getSelectedIndex();
                String text = textField.getText();
                
                if(text.trim().length() == 0){
                    sorter.setRowFilter(null);
                } else{
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)^" + Pattern.quote(text), columnIndex));
                }
            }
         
        });
    }
    
    // Calculate Discount prive
    private void calculateDiscountPrice(){
        ((AbstractDocument)discountProductSaleField.getDocument()).setDocumentFilter(new DocumentFilter(){
            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException{
                StringBuilder sb = new StringBuilder(fb.getDocument().getText(0,fb.getDocument().getLength()));
                sb.insert(offset, string);
                if(isValidDiscount(sb.toString())){
                    super.insertString(fb, offset, string, attr);
                }
            }
            
            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attr) throws BadLocationException{
                StringBuilder sb = new StringBuilder(fb.getDocument().getText(0,fb.getDocument().getLength()));
                sb.replace(offset,offset + length,text );
                if(isValidDiscount(sb.toString())){
                    super.replace(fb, offset, length, text, attr);
                }
            }
            
            @Override
            public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException{
                super.remove(fb, offset, length);
            }
            
            private boolean isValidDiscount(String text){
                if(text.isEmpty()) return true;
                try {
                    int val = Integer.parseInt(text);
                    return val >= 0 && val <=80;
                }catch(NumberFormatException e){
                    return false;
                }
            }
        });
        
        discountProductSaleField.getDocument().addDocumentListener(new DocumentListener(){
            public void insertUpdate(DocumentEvent e){
                calcular();
            }
            public void removeUpdate(DocumentEvent e){
                calcular();
            }
            public void changedUpdate(DocumentEvent e){
                calcular();
            }
            
            private void calcular(){
                try{
                    BigDecimal precioOriginal = new BigDecimal(listPriceProductSaleField.getText());
                    String descText = discountProductSaleField.getText();
                    if(descText.isEmpty()){
                        finalPriceProductSaleField.setText(listPriceProductSaleField.toString());
                        return;
                    }
                    int descuento = Integer.parseInt(descText);
                    BigDecimal descuentoBD = BigDecimal.valueOf(descuento).divide(BigDecimal.valueOf(100));
                    BigDecimal precioFinal = precioOriginal.subtract(precioOriginal.multiply(descuentoBD));
                    finalPriceProductSaleField.setText(precioFinal.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
                }catch(Exception e){
                    finalPriceProductSaleField.setText("0.00");
                }
                
            }
        });
    }
    
    private void calculateChange(){
        // Limitar payWithSaleField a números con 2 decimales
        ((AbstractDocument) payWithSaleField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
                sb.insert(offset, string);
                if (isValidDecimal(sb.toString())) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
                sb.replace(offset, offset + length, text);
                if (isValidDecimal(sb.toString())) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            private boolean isValidDecimal(String text) {
                return text.matches("^\\d{0,7}(\\.\\d{0,2})?$");
            }
        });
        Runnable changeCalculated = () -> {
            try {
                    BigDecimal total = new BigDecimal(totalPriceSaleField.getText());
                    String pagoStr = payWithSaleField.getText();

                    if (pagoStr.isEmpty()) {
                        changeSaleField.setText(total.negate().setScale(2, RoundingMode.HALF_UP).toString());
                    } else {
                        BigDecimal pago = new BigDecimal(pagoStr);
                        BigDecimal cambio = pago.subtract(total);
                        changeSaleField.setText(cambio.setScale(2, RoundingMode.HALF_UP).toString());
                    }
                } catch (Exception ex) {
                    changeSaleField.setText("Error");
                }
        };

        // Listener para calcular cambio dinámicamente
        payWithSaleField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { changeCalculated.run(); }
            @Override public void removeUpdate(DocumentEvent e) { changeCalculated.run(); }
            @Override public void changedUpdate(DocumentEvent e) { changeCalculated.run(); }
           
        });
        
        totalPriceSaleField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { changeCalculated.run(); }
            @Override public void removeUpdate(DocumentEvent e) { changeCalculated.run(); }
            @Override public void changedUpdate(DocumentEvent e) { changeCalculated.run(); }
           
        });
    }
    
    private void subtotalBuyCalculate(){
        ((AbstractDocument) buyPriceProductBuyField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;
                if (isValidNumber(newText)) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
                if (isValidNumber(newText)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            private boolean isValidNumber(String text) {
                // Permite números con punto o coma decimal, pero no ambos
                return text.matches("^\\d*([.,]\\d{0,2})?$") || text.isEmpty();
            }
        });

	 // Spinner para la cantidad (solo valores positivos)
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
        quantityBuySpinner.setModel(spinnerModel);

        // Configurar el editor del spinner para solo números
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(quantityBuySpinner, "#");
        quantityBuySpinner.setEditor(editor);
        
        JFormattedTextField tf = ((JSpinner.DefaultEditor) quantityBuySpinner.getEditor()).getTextField();
        tf.setColumns(4);
            
        // Función para actualizar el subtotal
        Runnable actualizarSubtotal = () -> {
            try {
                String priceText = buyPriceProductBuyField.getText().replace(',', '.');
                int quantity = (Integer) quantityBuySpinner.getValue();

                if (priceText.isEmpty() || priceText.equals(".") || priceText.equals(",")) {
                    subtotalBuyField.setText("0");
                    return;
                }

                BigDecimal price = new BigDecimal(priceText);
                BigDecimal subtotal = price.multiply(new BigDecimal(quantity));

                subtotalBuyField.setText(subtotal.setScale(2, RoundingMode.HALF_UP).toString());
            } catch (Exception ex) {
                subtotalBuyField.setText("0");
            }
        }; 
            
        // Listener para actualizar el subtotal cuando cambian los valores
        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { actualizarSubtotal.run(); }
            @Override
            public void removeUpdate(DocumentEvent e) { actualizarSubtotal.run(); }
            @Override
            public void changedUpdate(DocumentEvent e) { actualizarSubtotal.run(); }
        };

        buyPriceProductBuyField.getDocument().addDocumentListener(documentListener);

        quantityBuySpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                actualizarSubtotal.run();
            }
        });

            

    }
    
    private void codeSaleFieldConstraint(){
        ((AbstractDocument) codeSaleField.getDocument()).setDocumentFilter(new DocumentFilter(){
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException{
                if(string.matches("\\d+")){
                    super.insertString(fb, offset, string, attr);
                }
            }
            
            @Override
            public void replace(FilterBypass fb, int offset,int length, String text, AttributeSet attr) throws BadLocationException{
                if(text.matches("\\d+")){
                    super.replace(fb, offset, length, text, attr);
                }
            }
        });
    }
    
    // User Options
    private void fillUserTable(){
        String[] columnNames = {"ID","Nr. Documento", "Nombre", "Correo", "Rol", "Estado"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        List<User> users = userService.getAllUsers();
                
        for(User u : users){
            Object[] rowData = {
                u.getId(),
                u.getDocumentNumber(),
                u.getName(),
                u.getEmail(),
                u.getRole().getName(),
                u.isEnabled() ? "Activo" : "Inactivo"
            };
            model.addRow(rowData);
        }
                
        userTable.setModel(model);    
        userFilter();
        
        TableColumn col1 = userTable.getColumnModel().getColumn(0);
        col1.setMinWidth(30);
        col1.setMaxWidth(30);
        col1.setPreferredWidth(30);
    }
    
    private void cleanUserForm(){
        userDocumentField.setText("");
        userNameField.setText("");
        userEmailField.setText("");
        userPasswordField.setText("");
        confirmPasswordField.setText("");
        selectedUser = 0L;
    }
    
    // Category Options
    private void fillCategoryTable(){
        String[] columnNames = {"ID","Descripcion", "Estado"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        List<Category> categories = categoryService.allCategory();
        
        for(Category c : categories){
            
            Object[] rowData = {
                c.getId(),
                c.getDescription(),
                c.isEnabled() ? "Activo" : "Inactivo"
            };
            model.addRow(rowData);
        }
        
        categoryTable.setModel(model);   
        if(!categoryFlag){
            categoryFilter();
            categoryFlag = true;
        } 
        
        TableColumn col1 = categoryTable.getColumnModel().getColumn(0);
        col1.setMinWidth(30);
        col1.setMaxWidth(30);
        col1.setPreferredWidth(30);
    }
    
    private void cleanCategoryForm(){
        nameCategory.setText("");
        selectedCategory = 0L;
    }
    
    // Producto options
    
    private void fillProductTable(){
        String[] columnNames = {"ID","Codigo", "Nombre", "Descripcion", "Categoria", "Stock","Precio lista","Estado"};
        
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        List<Product> products = productService.allProducts();
        
        for(Product p : products){
            
            Object[] rowData = {
                p.getId(),
                p.getCode(),
                p.getName(),
                p.getDescription(),
                p.getCategory().getDescription(),
                p.getProductDetail().getStock(),
                p.getProductDetail().getListPrime(),
                p.getProductDetail().isEnabled() ? "Activo" : "Inactivo"           
            };
            model.addRow(rowData);
        }
        
        productTable.setModel(model);  
//        if(!productFlag){
//            productFlag = true;
//        }
        
        productFilter();

        TableColumn col1 = productTable.getColumnModel().getColumn(0);
        col1.setMinWidth(30);
        col1.setMaxWidth(30);
        col1.setPreferredWidth(30);                
    }
    
    private void cleanProductForm(){
        codeProductField.setText("");
        nameProductField.setText("");
        descriptionProductField.setText("");
        listPriceProductField.setText("");
        
        selectedProduct = 0L;
        selectedProductDetail = 0L;
    }
    
    private void fillCategoryProductCB(){
        categories = categoryService.allCategory();
       
        categoryProductCBForm.removeAllItems();
        
        categories.stream().forEach(c -> {
            categoryProductCBForm.addItem(c.getDescription());
        });
    }
    
    // Client Options
    
    private void fillClientTable(){
        String[] columnNames = {"ID","Nr. Documento", "Nombre", "Correo", "Telefono", "Estado"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        List<Client> clients = clientService.allClients();
                
        for(Client c : clients){
            Object[] rowData = {
                c.getId(),
                c.getDocumentNumber(),
                c.getName(),
                c.getEmail(),
                c.getPhoneNumber(),
                c.isEnabled() ? "Activo" : "Inactivo"
            };
            model.addRow(rowData);
        }
                
        clientTable.setModel(model);    
        String[] listClient = {"ID","Nr. Documento", "Nombre", "Correo", "Telefono", "Estado"};
        openFilter(clientTable,listClient, jComboBox11,jTextField14);
        
        TableColumn col1 = clientTable.getColumnModel().getColumn(0);
        col1.setMinWidth(30);
        col1.setMaxWidth(30);
        col1.setPreferredWidth(30);
        
    }
    
    private void cleanClientForm(){
        documentClientField.setText("");
        nameClientField.setText("");
        emailClientField.setText("");
        phoneClientField.setText("");
        selectedClient = 0L;
    }
    
    // Provider Options
    
    private void fillProviderTable(){
        String[] columnNames = {"ID","Nr. Documento", "Razon Social", "Correo", "Telefono", "Estado"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        List<Provider> providers = providerService.allProviders();
                
        for(Provider p : providers){
            Object[] rowData = {
                p.getId(),
                p.getNroRuc(),
                p.getRazonSocial(),
                p.getEmail(),
                p.getPhoneNumber(),
                p.isEnabled() ? "Activo" : "Inactivo"
            };
            model.addRow(rowData);
        }
                
        providerTable.setModel(model);    
        
        String[] listProvider = {"ID","Nr. Documento", "Nombre", "Correo", "Telefono", "Estado"};
        openFilter(providerTable,listProvider, jComboBox14,jTextField18);
        
        TableColumn col1 = providerTable.getColumnModel().getColumn(0);
        col1.setMinWidth(30);
        col1.setMaxWidth(30);
        col1.setPreferredWidth(30);
        
    }
    private void cleanProviderForm(){
        documentProviderField.setText("");
        nameProviderField.setText("");
        emailProviderField.setText("");
        phoneProviderField.setText("");
        selectedProvider = 0L;
    }
    
    // List Products Options
    
    private void fillListProductTable(){
        String[] columnNames = {"Codigo", "Nombre","Categoria"};
        
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        productsListTableSale = productService.allProducts().stream()
                .filter(p -> p.getProductDetail().isEnabled())
                .toList();
        
        
        List<Product> products = productsToSell.stream()
                .map(sd -> sd.getProduct())
                .toList();
        
        Set<Long> idsToExclude = products.stream()
                .map(Product::getId)
                .collect(Collectors.toSet());
        
        List<Product> filteresProducts = productsListTableSale.stream()
                .filter(p -> !idsToExclude.contains(p.getId()))
                .toList();
        
        for(Product p : filteresProducts){
            
            Object[] rowData = {
                p.getCode(),
                p.getName(),
                p.getCategory().getDescription(),
            };
            model.addRow(rowData);
        }
        
        listProductTable.setModel(model);
        listProductBuyTable.setModel(model);
        
        TableColumn col1 = listProductTable.getColumnModel().getColumn(0);
        col1.setMinWidth(60);
        col1.setMaxWidth(60);
        col1.setPreferredWidth(60);    
        
        TableColumn col2 = listProductBuyTable.getColumnModel().getColumn(0);
        col2.setMinWidth(60);
        col2.setMaxWidth(60);
        col2.setPreferredWidth(60); 
    }
    
        private void fillListProductBuyTable(){
        String[] columnNames = {"Codigo", "Nombre","Categoria"};
        
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        productsListTableOrder = productService.allProducts().stream()
                .filter(p -> p.getProductDetail().isEnabled())
                .toList();
        
        
        List<Product> products = productsToBuy.stream()
                .map(sd -> sd.getProduct())
                .toList();
        
        Set<Long> idsToExclude = products.stream()
                .map(Product::getId)
                .collect(Collectors.toSet());
        
        List<Product> filteresProducts = productsListTableOrder.stream()
                .filter(p -> !idsToExclude.contains(p.getId()))
                .toList();
        
        for(Product p : filteresProducts){
            
            Object[] rowData = {
                p.getCode(),
                p.getName(),
                p.getCategory().getDescription(),
            };
            model.addRow(rowData);
        }
        
        listProductBuyTable.setModel(model);        
        
        TableColumn col2 = listProductBuyTable.getColumnModel().getColumn(0);
        col2.setMinWidth(60);
        col2.setMaxWidth(60);
        col2.setPreferredWidth(60); 
    }
    
    private void fillListClientTable(){
        String[] columnNames = {"ID","Nro. Documento", "Nombre"};
        
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        clientListTableSale = clientService.allClients().stream()
                .filter(c -> c.isEnabled())
                .toList();
        
        for(Client c : clientListTableSale){
            
            Object[] rowData = {
                c.getId(),
                c.getDocumentNumber(),
                c.getName()
            };
            model.addRow(rowData);
        }
        
        listClientTable.setModel(model);
//        if(!clientSaleFlag){
//            String[] listClientCaleta = {"ID","Nro. Documento", "Nombre"};
//            openFilter(listClientTable,listClientCaleta, clientFilterOptionCB,clientFilterOptionField);
//            clientSaleFlag = true;
//        }  
        
        TableColumn col1 = listClientTable.getColumnModel().getColumn(0);
        col1.setMinWidth(60);
        col1.setMaxWidth(60);
        col1.setPreferredWidth(60); 
    }
    
    // Sale Options
    private void fillSaleTable(){
        String[] columnNames = {"Cod. Producto","Producto","Descuento", "Precio Venta", "Cantidad", "Subtotal"};
        
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        BigDecimal quantity;
        
        for(SaleDetail s : productsToSell){
            quantity = new BigDecimal(s.getQuantity());
            String subtotal = String.valueOf( quantity.multiply(s.getSalePrice()));
            
            Object[] rowData = {
                s.getProduct().getCode(),
                s.getProduct().getName(),
                s.getDiscount(),
                s.getSalePrice(),
                s.getQuantity(),
                subtotal
            };
            model.addRow(rowData);
        }
        
        saleTable.setModel(model);
        
    }
    
    private void cleanInfoProductSale(){         
        
        codeProductSaleField.setText("");
        nameProductSaleField.setText("");
        listPriceProductSaleField.setText("");
        stockProductSaleField.setText("");
        finalPriceProductSaleField.setText("");
        discountProductSaleField.setText("");
        
        SpinnerNumberModel model = new SpinnerNumberModel(0,0,0,1);
        quantitySaleSpinner.setModel(model);
        
        selectedProductToSale = null;
    }
    
    private void cleanSaleForm(){
        String[] columnNames = {"Cod. Producto","Producto","Descuento", "Precio Venta", "Cantidad", "Subtotal"};
        
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        saleTable.setModel(model);
        
        selectedClientToSale = null;
        docClientSaleField.setText("");
        nameSaleRegisterField.setText("");
        productsToSell.clear();
        totalPriceSaleField.setText("0.00");
        payWithSaleField.setText("0.00");
        changeSaleField.setText("0.00");
        cleanInfoProductSale();
    }
    
    // Provider options
    
    private void fillListProviderTable(){
        String[] columnNames = {"ID","RUC", "Razon Social"};
        
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        providerListTableBuy = providerService.allProviders().stream()
                .filter(p -> p.isEnabled())
                .toList();
        
        for(Provider p : providerListTableBuy){
            
            Object[] rowData = {
                p.getId(),
                p.getNroRuc(),
                p.getRazonSocial()
            };
            model.addRow(rowData);
        }
        
        listProviderTable.setModel(model);
//        if(!providerOrderFlag){
//            String[] listProviderCaleta = {"ID","RUC", "Razon Social"};
//            openFilter(listProviderTable,listProviderCaleta, jComboBox13,jTextField21);
//            providerOrderFlag = true;
//        }
        
        TableColumn col1 = listProviderTable.getColumnModel().getColumn(0);
        col1.setMinWidth(60);
        col1.setMaxWidth(60);
        col1.setPreferredWidth(60); 
    }
    
    // Report 
    
    
    private void fillReportSaleTable(){
        List<Sales> sales = saleService.allSales();
        
        String[] columnNames = {"Id de venta", "Fecha de venta", "Cliente", "Vendedor", "Tipo de Documento", "Monto total"};
        
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for(Sales s : sales){
            String total = s.getTotalAmount().toString();
            String fecha = s.getDate().format(formatter);
            
            Object[] rowData = {
                s.getId(),
                fecha,
                s.getClient().getName(),
                s.getUser().getName(),
                s.getDocumentType().toString(),
                total
            };
            model.addRow(rowData);
        }
        
        reportSaleTable.setModel(model);

        String[] listDetailsSale = {"Id de venta", "Fecha de venta", "Cliente", "Vendedor", "Tipo de Documento", "Monto total"};
        openFilter(reportSaleTable,listDetailsSale, jComboBox2,jTextField1);
    }
    
      private void fillReportOrderTable(){
        List<Orders> orders = orderService.allOrders();
        
        String[] columnNames = {"Id de compra", "Fecha de compra", "Proveedor", "Usuario", "Tipo de Documento", "Monto total"};
        
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for(Orders o : orders){
            String total = o.getTotalAmount().toString();
            String fecha = o.getDate().format(formatter);
            
            Object[] rowData = {
                o.getId(),
                fecha,
                o.getProvider().getRazonSocial(),
                o.getUser().getName(),
                o.getDocumentType().toString(),
                total
            };
            model.addRow(rowData);
        }
        
        reportOrderTable.setModel(model);

        String[] listDetailsOrder = {"Id de compra", "Fecha de compra", "Proveedor", "Usuario", "Tipo de Documento", "Monto total"};
        openFilter(reportOrderTable,listDetailsOrder, jComboBox3,jTextField2);
    }
    
    // Buy Options
    
    private void fillBuyTable(){
        String[] columnNames = {"Cod. Producto","Producto","Precio Compra", "Cantidad", "Subtotal"};
        
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        for(OrderDetail od : productsToBuy){
            
            Object[] rowData = {
                od.getProduct().getCode(),
                od.getProduct().getName(),
                od.getUnitPrice(),
                od.getQuantity(),
                od.getSubTotal()
            };
            model.addRow(rowData);
        }
        
        buyTable.setModel(model);
    }
    
    private void cleanInfoProductBuy(){
        codeProductBuyField.setText("");
        nameProductBuyField.setText("");
        buyPriceProductBuyField.setText("");
        stockProductBuyField.setText("");
        subtotalBuyField.setText("0");
        
        quantityBuySpinner.setValue(1);
        
        selectedProductToBuy = null;
    }
    
    private void cleanOrderForm(){
        String[] columnNames = {"Cod. Producto","Producto","Precio Compra", "Cantidad", "Subtotal"};
        
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        buyTable.setModel(model);
        selectedProviderToBuy = null;
        rucProvBuyField.setText("");        
        nameProvBuyField.setText("");
        
        productsToBuy.clear();
        
        totalPriceBuyField.setText("0.00");
        cleanInfoProductBuy();
    }
    
    // SALE DETAILS
    private void modelSaleDetailsTable(){
        String[] columnNames = {"Cod. Producto","Producto","Precio de lista","Descuento","Precio final" ,"Cantidad", "Subtotal"};
        
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        saleDetailsTable.setModel(model);
        
        TableColumn col1 = saleDetailsTable.getColumnModel().getColumn(0);
        col1.setMinWidth(70);
        col1.setMaxWidth(70);
        col1.setPreferredWidth(70);
    }
    
    private void fillSaleDetailsTable(Sales sale){
        String[] columnNames = {"Cod. Producto","Producto","Precio de lista","Descuento","Precio final" ,"Cantidad", "Subtotal"};
        
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
       
        
        Set<SaleDetail> saleDetails = sale.getSaleDetail();
       
        
        for(SaleDetail sd : saleDetails){
            BigDecimal subtotal = sd.getSalePrice().multiply(new BigDecimal(sd.getQuantity()));
            Object[] rowData = {
                sd.getProduct().getCode(),
                sd.getProduct().getName(),
                sd.getProduct().getProductDetail().getListPrime(),
                sd.getDiscount(),
                sd.getSalePrice(),
                sd.getQuantity(),
                subtotal
            };
            model.addRow(rowData);
        }
        
        saleDetailsTable.setModel(model);
        
        TableColumn col1 = saleDetailsTable.getColumnModel().getColumn(0);
        col1.setMinWidth(70);
        col1.setMaxWidth(70);
        col1.setPreferredWidth(70);
    }
    
    // ORDER DETAILS
    private void modelOrderDetailsTable(){
        String[] columnNames = {"Cod. Producto","Producto","Precio","Cantidad", "Subtotal"};
        
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        orderDetailsTable.setModel(model);
        
        TableColumn col1 = orderDetailsTable.getColumnModel().getColumn(0);
        col1.setMinWidth(70);
        col1.setMaxWidth(70);
        col1.setPreferredWidth(70);
    }
        
        
    private void fillOrderDetailsTable(Orders order){
        String[] columnNames = {"Cod. Producto","Producto","Precio","Cantidad", "Subtotal"};
        
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
       
        
        Set<OrderDetail> orderDetails = order.getOrderDetail();
       
        
        for(OrderDetail od : orderDetails){
            BigDecimal subtotal = od.getUnitPrice().multiply(new BigDecimal(od.getQuantity()));
            
            Object[] rowData = {
                od.getProduct().getCode(),
                od.getProduct().getName(),
                od.getUnitPrice(),
                od.getQuantity(),
                subtotal
            };
            model.addRow(rowData);
        }
        
        orderDetailsTable.setModel(model);
        
        TableColumn col1 = orderDetailsTable.getColumnModel().getColumn(0);
        col1.setMinWidth(70);
        col1.setMaxWidth(70);
        col1.setPreferredWidth(70);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sellerSubmenu = new javax.swing.JPopupMenu();
        mantainerSubmenu = new javax.swing.JPopupMenu();
        shopSubmenu = new javax.swing.JPopupMenu();
        reportSubmenu = new javax.swing.JPopupMenu();
        productSelectDialog = new javax.swing.JDialog();
        jPanel20 = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jComboBox9 = new javax.swing.JComboBox<>();
        jTextField17 = new javax.swing.JTextField();
        jButton17 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        listProductTable = new javax.swing.JTable();
        clientSelectDialog = new javax.swing.JDialog();
        jPanel21 = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        clientFilterOptionCB = new javax.swing.JComboBox<>();
        clientFilterOptionField = new javax.swing.JTextField();
        jButton18 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        listClientTable = new javax.swing.JTable();
        productBuySelectDialog = new javax.swing.JDialog();
        jPanel22 = new javax.swing.JPanel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jComboBox12 = new javax.swing.JComboBox<>();
        jTextField20 = new javax.swing.JTextField();
        jButton21 = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        listProductBuyTable = new javax.swing.JTable();
        providerSelectDialog = new javax.swing.JDialog();
        jPanel23 = new javax.swing.JPanel();
        jLabel82 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jComboBox13 = new javax.swing.JComboBox<>();
        jTextField21 = new javax.swing.JTextField();
        jButton22 = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        listProviderTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        currentUsername = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        sellerButton = new javax.swing.JButton();
        clientButton = new javax.swing.JButton();
        reportButton = new javax.swing.JButton();
        aboutButton = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        userPane = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        userDocumentField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        userNameField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        userEmailField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        confirmPasswordField = new javax.swing.JPasswordField();
        userPasswordField = new javax.swing.JPasswordField();
        userRoleCB = new javax.swing.JComboBox<>();
        userStatusCB = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cleanUserButton = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        deleteUserButton = new javax.swing.JButton();
        saveUserButton = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        userFilterCB = new javax.swing.JComboBox<>();
        userFilterField = new javax.swing.JTextField();
        clearFilterField = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        categoryPane = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        categoryStatusCB = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        saveCategoryButton = new javax.swing.JButton();
        cleanCategoryButton = new javax.swing.JButton();
        deleteCategoryButton = new javax.swing.JButton();
        nameCategory = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        categoryFilterCB = new javax.swing.JComboBox<>();
        categoryFilterField = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        categoryTable = new javax.swing.JTable();
        productPane = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        codeProductField = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        descriptionProductField = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        categoryProductCBForm = new javax.swing.JComboBox<>();
        statusProductCB = new javax.swing.JComboBox<>();
        categoryProductCB = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        cleanProductButton = new javax.swing.JButton();
        deleteProductButton = new javax.swing.JButton();
        saveProductButton = new javax.swing.JButton();
        nameProductField = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        listPriceProductField = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        productFilterCB = new javax.swing.JComboBox<>();
        productFilterField = new javax.swing.JTextField();
        jButton14 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        productTable = new javax.swing.JTable();
        bussinessPane = new javax.swing.JPanel();
        sellerRegisterPane = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        sellInfoPanel = new javax.swing.JPanel();
        dateSaleField = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        docTypeSaleFieldCB = new javax.swing.JComboBox<>();
        jLabel41 = new javax.swing.JLabel();
        sellProviderPanel = new javax.swing.JPanel();
        docClientSaleField = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        nameSaleRegisterField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        sellProductPanel = new javax.swing.JPanel();
        codeProductSaleField = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        nameProductSaleField = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        listPriceProductSaleField = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        stockProductSaleField = new javax.swing.JTextField();
        quantitySaleSpinner = new javax.swing.JSpinner();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        finalPriceProductSaleField = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        discountProductSaleField = new javax.swing.JTextField();
        addProductSaleButton = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        saleTable = new javax.swing.JTable();
        jLabel62 = new javax.swing.JLabel();
        totalPriceSaleField = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        payWithSaleField = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        changeSaleField = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        deleteProductSaleButton = new javax.swing.JButton();
        sellerDetailsPane = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel86 = new javax.swing.JLabel();
        infoClientePanel = new javax.swing.JPanel();
        docClientDetailSale = new javax.swing.JTextField();
        jLabel91 = new javax.swing.JLabel();
        nameClientDetailSale = new javax.swing.JTextField();
        jLabel92 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        codeSaleField = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        infoVentaPanel1 = new javax.swing.JPanel();
        dateDetailSale = new javax.swing.JTextField();
        docTypeDetailSale = new javax.swing.JTextField();
        jLabel88 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        userNameDetailSale = new javax.swing.JTextField();
        jLabel90 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        saleDetailsTable = new javax.swing.JTable();
        jLabel93 = new javax.swing.JLabel();
        totalPriceDetailSale = new javax.swing.JTextField();
        jLabel94 = new javax.swing.JLabel();
        clientAmountDetailsSale = new javax.swing.JTextField();
        jLabel95 = new javax.swing.JLabel();
        changeSaleDetailSale = new javax.swing.JTextField();
        jButton13 = new javax.swing.JButton();
        shopRegisterPane = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        buyInfoPanel = new javax.swing.JPanel();
        dateBuyField = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        docTypeBuyFieldCB = new javax.swing.JComboBox<>();
        jLabel72 = new javax.swing.JLabel();
        buyProviderPanel = new javax.swing.JPanel();
        rucProvBuyField = new javax.swing.JTextField();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        nameProvBuyField = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        buyProductPanel = new javax.swing.JPanel();
        codeProductBuyField = new javax.swing.JTextField();
        jLabel75 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        nameProductBuyField = new javax.swing.JTextField();
        jLabel76 = new javax.swing.JLabel();
        stockProductBuyField = new javax.swing.JTextField();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        quantityBuySpinner = new javax.swing.JSpinner();
        subtotalBuyField = new javax.swing.JTextField();
        jLabel81 = new javax.swing.JLabel();
        buyPriceProductBuyField = new javax.swing.JTextField();
        jLabel85 = new javax.swing.JLabel();
        addProductSaleButton1 = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        buyTable = new javax.swing.JTable();
        jLabel83 = new javax.swing.JLabel();
        totalPriceBuyField = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        deleteProductSaleButton1 = new javax.swing.JButton();
        shopDetailsPane = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jLabel96 = new javax.swing.JLabel();
        infoClientePanel1 = new javax.swing.JPanel();
        rucDetailOrder = new javax.swing.JTextField();
        jLabel97 = new javax.swing.JLabel();
        nameProviderDetailOrder = new javax.swing.JTextField();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        codeOrderField = new javax.swing.JTextField();
        jButton16 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        infoVentaPanel2 = new javax.swing.JPanel();
        dateDetailOrder = new javax.swing.JTextField();
        docTypeDetailOrder = new javax.swing.JTextField();
        jLabel100 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        userNameDetailOrder = new javax.swing.JTextField();
        jLabel102 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        orderDetailsTable = new javax.swing.JTable();
        jLabel103 = new javax.swing.JLabel();
        totalPriceDetailOrder = new javax.swing.JTextField();
        jButton27 = new javax.swing.JButton();
        clientPane = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        documentClientField = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        nameClientField = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        emailClientField = new javax.swing.JTextField();
        statusClientCB = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        cleanClientFormButton = new javax.swing.JButton();
        deleteClientButton = new javax.swing.JButton();
        saveClientButton = new javax.swing.JButton();
        phoneClientField = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jComboBox11 = new javax.swing.JComboBox<>();
        jTextField14 = new javax.swing.JTextField();
        jButton19 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        clientTable = new javax.swing.JTable();
        providerPane = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        documentProviderField = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        nameProviderField = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        emailProviderField = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        phoneProviderField = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        statusProviderCB = new javax.swing.JComboBox<>();
        saveProviderButton = new javax.swing.JButton();
        cleanProviderFormButton = new javax.swing.JButton();
        deleteProviderButton = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jComboBox14 = new javax.swing.JComboBox<>();
        jTextField18 = new javax.swing.JTextField();
        jButton24 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        providerTable = new javax.swing.JTable();
        reportSellPane = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jLabel104 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel108 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jPanel27 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        reportOrderTable = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        reportShopPane = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        reportSaleTable = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();
        jPanel29 = new javax.swing.JPanel();
        jLabel107 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();

        productSelectDialog.setResizable(false);
        productSelectDialog.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel65.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel65.setText("Lista de productos:");

        jLabel66.setText("Buscar por:");

        jButton17.setText("jButton4");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jLabel66)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel65)
                .addGap(2, 2, 2)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel66)
                    .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton17))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        productSelectDialog.getContentPane().add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 510, 80));

        listProductTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        listProductTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listProductTableMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(listProductTable);

        productSelectDialog.getContentPane().add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 510, 270));

        clientSelectDialog.setResizable(false);
        clientSelectDialog.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel67.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel67.setText("Lista de clientes:");

        jLabel68.setText("Buscar por:");

        jButton18.setText("jButton4");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jLabel68)
                        .addGap(18, 18, 18)
                        .addComponent(clientFilterOptionCB, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(clientFilterOptionField, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel67)
                .addGap(2, 2, 2)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel68)
                    .addComponent(clientFilterOptionCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clientFilterOptionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton18))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        clientSelectDialog.getContentPane().add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 510, 80));

        listClientTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        listClientTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listClientTableMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(listClientTable);

        clientSelectDialog.getContentPane().add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 510, 270));

        productBuySelectDialog.setResizable(false);
        productBuySelectDialog.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel79.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel79.setText("Lista de productos:");

        jLabel80.setText("Buscar por:");

        jButton21.setText("jButton4");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jLabel80)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel79)
                .addGap(2, 2, 2)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel80)
                    .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton21))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        productBuySelectDialog.getContentPane().add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 510, 80));

        listProductBuyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        listProductBuyTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listProductBuyTableMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(listProductBuyTable);

        productBuySelectDialog.getContentPane().add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 510, 270));

        providerSelectDialog.setResizable(false);
        providerSelectDialog.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel82.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel82.setText("Lista de proveedores:");

        jLabel84.setText("Buscar por:");

        jButton22.setText("jButton4");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel23Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jLabel84)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel82)
                .addGap(2, 2, 2)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel84)
                    .addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton22))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        providerSelectDialog.getContentPane().add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 510, 80));

        listProviderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        listProviderTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listProviderTableMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(listProviderTable);

        providerSelectDialog.getContentPane().add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 510, 270));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(72, 130, 180));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Sistema de ventas");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 380, 40));

        jLabel105.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel105.setForeground(new java.awt.Color(255, 255, 255));
        jLabel105.setText("Usuario:");
        jPanel1.add(jLabel105, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 20, -1, 20));

        currentUsername.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        currentUsername.setForeground(new java.awt.Color(255, 255, 255));
        currentUsername.setText("jLabel109");
        jPanel1.add(currentUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 20, 90, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 60));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sellerButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/META-INF/grafico-de-barras.png"))); // NOI18N
        sellerButton.setText("Ventas");
        sellerButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sellerButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        sellerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sellerButtonActionPerformed(evt);
            }
        });
        jPanel2.add(sellerButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 70));

        clientButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/META-INF/cliente.png"))); // NOI18N
        clientButton.setText("Clientes");
        clientButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        clientButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        clientButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientButtonActionPerformed(evt);
            }
        });
        jPanel2.add(clientButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, 90, 70));

        reportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/META-INF/reporte.png"))); // NOI18N
        reportButton.setText("Reportes");
        reportButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        reportButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        reportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportButtonActionPerformed(evt);
            }
        });
        jPanel2.add(reportButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 0, 90, 70));

        aboutButton.setText("Acerca de");
        jPanel2.add(aboutButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 90, 70));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 1120, 70));

        userPane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Detalles del usuario");
        jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 190, 50));
        jPanel5.add(userDocumentField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 200, -1));

        jLabel2.setText("Nro. Documento:");
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 140, -1));
        jPanel5.add(userNameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 200, -1));

        jLabel4.setText("Email:");
        jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 140, -1));
        jPanel5.add(userEmailField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 200, -1));

        jLabel5.setText("Rol:");
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 140, -1));

        jLabel6.setText("Contraseña:");
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 140, -1));

        jLabel7.setText("Confirmar contraseña:");
        jPanel5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 140, -1));
        jPanel5.add(confirmPasswordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 200, -1));
        jPanel5.add(userPasswordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 200, -1));

        jPanel5.add(userRoleCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 200, -1));

        jPanel5.add(userStatusCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 200, -1));

        jLabel9.setText("Estado:");
        jPanel5.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 140, -1));

        jLabel10.setText("Nombres:");
        jPanel5.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 140, -1));

        cleanUserButton.setBackground(new java.awt.Color(65, 105, 226));
        cleanUserButton.setForeground(new java.awt.Color(255, 255, 255));
        cleanUserButton.setText("Limpiar");
        cleanUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cleanUserButtonActionPerformed(evt);
            }
        });
        jPanel5.add(cleanUserButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, 200, -1));

        jLabel8.setText("Email:");
        jPanel5.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 140, -1));

        deleteUserButton.setBackground(new java.awt.Color(178, 34, 34));
        deleteUserButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteUserButton.setText("Eliminar");
        deleteUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteUserButtonActionPerformed(evt);
            }
        });
        jPanel5.add(deleteUserButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 520, 200, -1));

        saveUserButton.setBackground(new java.awt.Color(36, 139, 34));
        saveUserButton.setForeground(new java.awt.Color(255, 255, 255));
        saveUserButton.setText("Guardar");
        saveUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveUserButtonActionPerformed(evt);
            }
        });
        jPanel5.add(saveUserButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, 200, -1));

        jLabel26.setForeground(new java.awt.Color(255, 51, 51));
        jLabel26.setText("*");
        jPanel5.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 60, 20, -1));

        jLabel32.setForeground(new java.awt.Color(255, 51, 51));
        jLabel32.setText("*");
        jPanel5.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, 20, -1));

        jLabel34.setForeground(new java.awt.Color(255, 51, 51));
        jLabel34.setText("*");
        jPanel5.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 210, 20, -1));

        jLabel37.setForeground(new java.awt.Color(255, 51, 51));
        jLabel37.setText("*");
        jPanel5.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 270, 20, -1));

        jLabel43.setForeground(new java.awt.Color(255, 51, 51));
        jLabel43.setText("*");
        jPanel5.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, 20, -1));

        userPane.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 610));

        jPanel4.setBackground(new java.awt.Color(72, 130, 180));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setText("Lista de usuarios:");

        jLabel12.setText("Buscar por:");

        clearFilterField.setText("jButton4");
        clearFilterField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearFilterFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(userFilterCB, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(userFilterField, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(clearFilterField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(userFilterCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userFilterField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearFilterField))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 810, 60));

        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                ".", ".", "Nro. Documento", "Nombres", "Correo", "Rol", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        userTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(userTable);
        if (userTable.getColumnModel().getColumnCount() > 0) {
            userTable.getColumnModel().getColumn(0).setResizable(false);
            userTable.getColumnModel().getColumn(1).setResizable(false);
            userTable.getColumnModel().getColumn(2).setResizable(false);
            userTable.getColumnModel().getColumn(3).setResizable(false);
            userTable.getColumnModel().getColumn(4).setResizable(false);
            userTable.getColumnModel().getColumn(5).setResizable(false);
            userTable.getColumnModel().getColumn(6).setResizable(false);
        }

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 130, 810, 370));

        userPane.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 870, 570));

        jTabbedPane1.addTab("tab1", userPane);

        categoryPane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setText("Detalles de categoria");
        jPanel7.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 190, 50));

        jLabel14.setText("Nombre:");
        jPanel7.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 140, -1));

        jPanel7.add(categoryStatusCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 200, -1));

        jLabel20.setText("Estado:");
        jPanel7.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 140, -1));

        saveCategoryButton.setBackground(new java.awt.Color(36, 139, 34));
        saveCategoryButton.setForeground(new java.awt.Color(255, 255, 255));
        saveCategoryButton.setText("Guardar");
        saveCategoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveCategoryButtonActionPerformed(evt);
            }
        });
        jPanel7.add(saveCategoryButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 200, -1));

        cleanCategoryButton.setBackground(new java.awt.Color(65, 105, 226));
        cleanCategoryButton.setForeground(new java.awt.Color(255, 255, 255));
        cleanCategoryButton.setText("Limpiar");
        cleanCategoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cleanCategoryButtonActionPerformed(evt);
            }
        });
        jPanel7.add(cleanCategoryButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 200, -1));

        deleteCategoryButton.setBackground(new java.awt.Color(178, 34, 34));
        deleteCategoryButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteCategoryButton.setText("Eliminar");
        deleteCategoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteCategoryButtonActionPerformed(evt);
            }
        });
        jPanel7.add(deleteCategoryButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 200, -1));
        jPanel7.add(nameCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 200, -1));

        jLabel24.setForeground(new java.awt.Color(255, 51, 51));
        jLabel24.setText("*");
        jPanel7.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 120, 20, -1));

        categoryPane.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 610));

        jPanel8.setBackground(new java.awt.Color(72, 130, 180));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setText("Lista de categorias:");

        jLabel16.setText("Buscar por:");

        jButton6.setText("jButton4");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89)
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(categoryFilterCB, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(categoryFilterField, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(categoryFilterCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(categoryFilterField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel8.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 810, 60));

        categoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                ".", ".", "Nombre", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        categoryTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                categoryTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(categoryTable);
        if (categoryTable.getColumnModel().getColumnCount() > 0) {
            categoryTable.getColumnModel().getColumn(0).setResizable(false);
            categoryTable.getColumnModel().getColumn(1).setResizable(false);
            categoryTable.getColumnModel().getColumn(2).setResizable(false);
            categoryTable.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel8.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 130, 810, 370));

        categoryPane.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 880, 580));

        jTabbedPane1.addTab("tab2", categoryPane);

        productPane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setText("Detalles del producto");
        jPanel10.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 190, 50));

        jLabel18.setText("Codigo:");
        jPanel10.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 140, -1));
        jPanel10.add(codeProductField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 200, -1));

        jLabel19.setText("Descripcion:");
        jPanel10.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 140, -1));
        jPanel10.add(descriptionProductField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 200, -1));

        jLabel21.setText("Categoria:");
        jPanel10.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 140, -1));

        jPanel10.add(categoryProductCBForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 200, -1));

        jPanel10.add(statusProductCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 200, -1));

        categoryProductCB.setText("Estado:");
        jPanel10.add(categoryProductCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 140, -1));

        jLabel25.setText("Nombre:");
        jPanel10.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 140, -1));

        cleanProductButton.setBackground(new java.awt.Color(65, 105, 226));
        cleanProductButton.setForeground(new java.awt.Color(255, 255, 255));
        cleanProductButton.setText("Limpiar");
        cleanProductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cleanProductButtonActionPerformed(evt);
            }
        });
        jPanel10.add(cleanProductButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 200, -1));

        deleteProductButton.setBackground(new java.awt.Color(178, 34, 34));
        deleteProductButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteProductButton.setText("Eliminar");
        deleteProductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteProductButtonActionPerformed(evt);
            }
        });
        jPanel10.add(deleteProductButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 200, -1));

        saveProductButton.setBackground(new java.awt.Color(36, 139, 34));
        saveProductButton.setForeground(new java.awt.Color(255, 255, 255));
        saveProductButton.setText("Guardar");
        saveProductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveProductButtonActionPerformed(evt);
            }
        });
        jPanel10.add(saveProductButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 200, -1));
        jPanel10.add(nameProductField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 200, -1));

        jLabel22.setForeground(new java.awt.Color(255, 51, 51));
        jLabel22.setText("*");
        jPanel10.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, 20, -1));

        jLabel23.setForeground(new java.awt.Color(255, 51, 51));
        jLabel23.setText("*");
        jPanel10.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 60, 20, -1));
        jPanel10.add(listPriceProductField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 200, -1));

        jLabel56.setText("Precio de Lista");
        jPanel10.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 140, -1));

        productPane.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 610));

        jPanel11.setBackground(new java.awt.Color(72, 130, 180));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel27.setText("Lista de productos:");

        jLabel28.setText("Buscar por:");

        jButton14.setText("jButton4");

        jButton26.setText("Descargar Excel");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(128, 128, 128)
                .addComponent(jLabel28)
                .addGap(18, 18, 18)
                .addComponent(productFilterCB, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(productFilterField, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton26))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(productFilterCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(productFilterField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton14))))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel11.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 810, 90));

        productTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                ".", ".", "Codigo", "Nombre", "Descripcion", "Categoria", "Stock", "Precio compra", "Precio venta", "Estado"
            }
        ));
        productTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(productTable);
        if (productTable.getColumnModel().getColumnCount() > 0) {
            productTable.getColumnModel().getColumn(0).setResizable(false);
            productTable.getColumnModel().getColumn(1).setResizable(false);
            productTable.getColumnModel().getColumn(2).setResizable(false);
            productTable.getColumnModel().getColumn(3).setResizable(false);
            productTable.getColumnModel().getColumn(4).setResizable(false);
            productTable.getColumnModel().getColumn(5).setResizable(false);
            productTable.getColumnModel().getColumn(6).setResizable(false);
        }

        jPanel11.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 810, 370));

        productPane.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 870, 570));

        jTabbedPane1.addTab("tab3", productPane);

        bussinessPane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jTabbedPane1.addTab("tab4", bussinessPane);

        sellerRegisterPane.setBackground(new java.awt.Color(72, 130, 180));
        sellerRegisterPane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sellInfoPanel.setBackground(new java.awt.Color(255, 255, 255));
        sellInfoPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sellInfoPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dateSaleField.setEditable(false);
        sellInfoPanel.add(dateSaleField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 80, 20));

        jLabel42.setText("Tipo de documento:");
        sellInfoPanel.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, -1, -1));

        jLabel44.setText("Fecha:");
        sellInfoPanel.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        sellInfoPanel.add(docTypeSaleFieldCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 180, 20));

        jPanel3.add(sellInfoPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 330, 80));

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel41.setText("Registrar Venta");
        jPanel3.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, -1));

        sellProviderPanel.setBackground(new java.awt.Color(255, 255, 255));
        sellProviderPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sellProviderPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        docClientSaleField.setEditable(false);
        sellProviderPanel.add(docClientSaleField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 147, 20));

        jLabel46.setText("Numero Documento:");
        sellProviderPanel.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel47.setText("Nombre:");
        sellProviderPanel.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 108, -1));

        nameSaleRegisterField.setEditable(false);
        sellProviderPanel.add(nameSaleRegisterField, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, 190, 20));

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        sellProviderPanel.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 28, 20));

        jPanel3.add(sellProviderPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, 440, 80));

        sellProductPanel.setBackground(new java.awt.Color(255, 255, 255));
        sellProductPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sellProductPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        codeProductSaleField.setEditable(false);
        sellProductPanel.add(codeProductSaleField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 147, -1));

        jLabel54.setText("Codigo:");
        sellProductPanel.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        sellProductPanel.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 49, 28, 20));

        nameProductSaleField.setEditable(false);
        sellProductPanel.add(nameProductSaleField, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 50, 160, -1));

        jLabel55.setText("Producto:");
        sellProductPanel.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, 108, -1));

        listPriceProductSaleField.setEditable(false);
        listPriceProductSaleField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        sellProductPanel.add(listPriceProductSaleField, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 50, 88, -1));

        jLabel57.setText("Precio de Lista:");
        sellProductPanel.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, 108, -1));

        jLabel58.setText("Cantidad");
        sellProductPanel.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 30, 50, -1));

        stockProductSaleField.setEditable(false);
        stockProductSaleField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        sellProductPanel.add(stockProductSaleField, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 50, 50, -1));
        sellProductPanel.add(quantitySaleSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 50, -1, -1));

        jLabel59.setText("Stock:");
        sellProductPanel.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 30, 50, -1));

        jLabel60.setText("Descuento:");
        sellProductPanel.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 30, 70, -1));

        finalPriceProductSaleField.setEditable(false);
        finalPriceProductSaleField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        sellProductPanel.add(finalPriceProductSaleField, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 50, 90, -1));

        jLabel61.setText("Precio de venta:");
        sellProductPanel.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 30, 100, -1));

        jLabel69.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel69.setText("%");
        sellProductPanel.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 50, 30, -1));

        discountProductSaleField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        sellProductPanel.add(discountProductSaleField, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 50, 50, -1));

        jPanel3.add(sellProductPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 830, 90));

        addProductSaleButton.setText("Agregar");
        addProductSaleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductSaleButtonActionPerformed(evt);
            }
        });
        jPanel3.add(addProductSaleButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 160, 90, 50));

        saleTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        saleTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saleTableMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(saleTable);

        jPanel3.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, 830, 220));

        jLabel62.setText("Total a pagar:");
        jPanel3.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 290, -1, -1));

        totalPriceSaleField.setEditable(false);
        totalPriceSaleField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jPanel3.add(totalPriceSaleField, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 310, 90, -1));

        jLabel63.setText("Paga con:");
        jPanel3.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 340, -1, -1));

        payWithSaleField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jPanel3.add(payWithSaleField, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 360, 90, -1));

        jLabel64.setText("Cambio:");
        jPanel3.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 390, -1, -1));

        changeSaleField.setEditable(false);
        changeSaleField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jPanel3.add(changeSaleField, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 410, 90, -1));

        jButton8.setText("Crear Venta");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 440, 90, 50));

        deleteProductSaleButton.setText("Eliminar");
        deleteProductSaleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteProductSaleButtonActionPerformed(evt);
            }
        });
        jPanel3.add(deleteProductSaleButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 220, 90, -1));

        sellerRegisterPane.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 1000, 510));

        jTabbedPane1.addTab("tab5", sellerRegisterPane);

        sellerDetailsPane.setBackground(new java.awt.Color(72, 130, 180));
        sellerDetailsPane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel86.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel86.setText("Detalle de Venta");
        jPanel24.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        infoClientePanel.setBackground(new java.awt.Color(255, 255, 255));
        infoClientePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        infoClientePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        docClientDetailSale.setEditable(false);
        infoClientePanel.add(docClientDetailSale, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 142, -1));

        jLabel91.setText("Doc. Cliente:");
        infoClientePanel.add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 110, -1));

        nameClientDetailSale.setEditable(false);
        infoClientePanel.add(nameClientDetailSale, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 190, -1));

        jLabel92.setText("Nombre del cliente:");
        infoClientePanel.add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 141, -1));

        jPanel24.add(infoClientePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 790, 80));

        jLabel87.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel87.setText("Codigo de venta:");
        jPanel24.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, -1, -1));
        jPanel24.add(codeSaleField, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 150, -1));

        jButton11.setText("Limpiar");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel24.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 20, -1, -1));

        jButton12.setText("Buscar");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel24.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, -1, -1));

        infoVentaPanel1.setBackground(new java.awt.Color(255, 255, 255));
        infoVentaPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        infoVentaPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dateDetailSale.setEditable(false);
        infoVentaPanel1.add(dateDetailSale, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 142, -1));

        docTypeDetailSale.setEditable(false);
        infoVentaPanel1.add(docTypeDetailSale, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 190, -1));

        jLabel88.setText("Fecha:");
        infoVentaPanel1.add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 110, -1));

        jLabel89.setText("Tipo de documento");
        infoVentaPanel1.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 141, -1));

        userNameDetailSale.setEditable(false);
        infoVentaPanel1.add(userNameDetailSale, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, 193, -1));

        jLabel90.setText("Vendedor:");
        infoVentaPanel1.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 20, 98, -1));

        jPanel24.add(infoVentaPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 790, 80));

        saleDetailsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane12.setViewportView(saleDetailsTable);

        jPanel24.add(jScrollPane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 790, 240));

        jLabel93.setText("Monto total:");
        jPanel24.add(jLabel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 500, 70, -1));

        totalPriceDetailSale.setEditable(false);
        jPanel24.add(totalPriceDetailSale, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 500, 80, -1));

        jLabel94.setText("Monto pago:");
        jPanel24.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 500, -1, -1));

        clientAmountDetailsSale.setEditable(false);
        jPanel24.add(clientAmountDetailsSale, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 500, 80, -1));

        jLabel95.setText("Cambio:");
        jPanel24.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 500, -1, -1));

        changeSaleDetailSale.setEditable(false);
        jPanel24.add(changeSaleDetailSale, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 500, 80, -1));

        jButton13.setText("Descargar como PDF");
        jPanel24.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 500, 180, -1));

        sellerDetailsPane.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 870, 540));

        jTabbedPane1.addTab("tab6", sellerDetailsPane);

        shopRegisterPane.setBackground(new java.awt.Color(72, 130, 180));
        shopRegisterPane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        buyInfoPanel.setBackground(new java.awt.Color(255, 255, 255));
        buyInfoPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        buyInfoPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dateBuyField.setEditable(false);
        buyInfoPanel.add(dateBuyField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 80, 20));

        jLabel70.setText("Tipo de documento:");
        buyInfoPanel.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, -1, -1));

        jLabel71.setText("Fecha:");
        buyInfoPanel.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        buyInfoPanel.add(docTypeBuyFieldCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 180, 20));

        jPanel15.add(buyInfoPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 330, 80));

        jLabel72.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel72.setText("Registrar Compra");
        jPanel15.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, -1));

        buyProviderPanel.setBackground(new java.awt.Color(255, 255, 255));
        buyProviderPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        buyProviderPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rucProvBuyField.setEditable(false);
        buyProviderPanel.add(rucProvBuyField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 147, 20));

        jLabel73.setText("RUC");
        buyProviderPanel.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel74.setText("Nombre:");
        buyProviderPanel.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 108, -1));

        nameProvBuyField.setEditable(false);
        buyProviderPanel.add(nameProvBuyField, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, 190, 20));

        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        buyProviderPanel.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 28, 20));

        jPanel15.add(buyProviderPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, 440, 80));

        buyProductPanel.setBackground(new java.awt.Color(255, 255, 255));
        buyProductPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        buyProductPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        codeProductBuyField.setEditable(false);
        buyProductPanel.add(codeProductBuyField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 147, -1));

        jLabel75.setText("Codigo:");
        buyProductPanel.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        buyProductPanel.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 49, 28, 20));

        nameProductBuyField.setEditable(false);
        buyProductPanel.add(nameProductBuyField, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 50, 160, -1));

        jLabel76.setText("Producto:");
        buyProductPanel.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, 108, -1));

        stockProductBuyField.setEditable(false);
        buyProductPanel.add(stockProductBuyField, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 70, -1));

        jLabel77.setText("Stock:");
        buyProductPanel.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 30, 60, -1));

        jLabel78.setText("Cantidad");
        buyProductPanel.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 30, 50, -1));
        buyProductPanel.add(quantityBuySpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 50, -1, -1));

        subtotalBuyField.setEditable(false);
        buyProductPanel.add(subtotalBuyField, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 50, 80, -1));

        jLabel81.setText("Subtotal:");
        buyProductPanel.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 30, 108, -1));

        buyPriceProductBuyField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        buyProductPanel.add(buyPriceProductBuyField, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, 90, -1));

        jLabel85.setText("Precio de compra:");
        buyProductPanel.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 30, 108, -1));

        jPanel15.add(buyProductPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 830, 90));

        addProductSaleButton1.setText("Agregar");
        addProductSaleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductSaleButton1ActionPerformed(evt);
            }
        });
        jPanel15.add(addProductSaleButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 160, 90, 50));

        buyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        buyTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buyTableMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(buyTable);

        jPanel15.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, 830, 220));

        jLabel83.setText("Total a pagar:");
        jPanel15.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 390, -1, -1));

        totalPriceBuyField.setEditable(false);
        jPanel15.add(totalPriceBuyField, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 410, 90, -1));

        jButton10.setText("Registrar");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel15.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 440, 90, 50));

        deleteProductSaleButton1.setText("Eliminar");
        deleteProductSaleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteProductSaleButton1ActionPerformed(evt);
            }
        });
        jPanel15.add(deleteProductSaleButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 220, 90, -1));

        shopRegisterPane.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 1000, 510));

        jTabbedPane1.addTab("tab7", shopRegisterPane);

        shopDetailsPane.setBackground(new java.awt.Color(72, 130, 180));
        shopDetailsPane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel96.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel96.setText("Detalle de Compra");
        jPanel25.add(jLabel96, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        infoClientePanel1.setBackground(new java.awt.Color(255, 255, 255));
        infoClientePanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        infoClientePanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rucDetailOrder.setEditable(false);
        infoClientePanel1.add(rucDetailOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 142, -1));

        jLabel97.setText("R.U.C:");
        infoClientePanel1.add(jLabel97, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 110, -1));

        nameProviderDetailOrder.setEditable(false);
        infoClientePanel1.add(nameProviderDetailOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 190, -1));

        jLabel98.setText("Razon Social:");
        infoClientePanel1.add(jLabel98, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 141, -1));

        jPanel25.add(infoClientePanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 790, 80));

        jLabel99.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel99.setText("Codigo de compra:");
        jPanel25.add(jLabel99, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, -1, -1));
        jPanel25.add(codeOrderField, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 150, -1));

        jButton16.setText("Limpiar");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jPanel25.add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 20, -1, -1));

        jButton23.setText("Buscar");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });
        jPanel25.add(jButton23, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, -1, -1));

        infoVentaPanel2.setBackground(new java.awt.Color(255, 255, 255));
        infoVentaPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        infoVentaPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dateDetailOrder.setEditable(false);
        infoVentaPanel2.add(dateDetailOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 142, -1));

        docTypeDetailOrder.setEditable(false);
        infoVentaPanel2.add(docTypeDetailOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 190, -1));

        jLabel100.setText("Fecha:");
        infoVentaPanel2.add(jLabel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 110, -1));

        jLabel101.setText("Tipo de documento");
        infoVentaPanel2.add(jLabel101, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 141, -1));

        userNameDetailOrder.setEditable(false);
        infoVentaPanel2.add(userNameDetailOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, 193, -1));

        jLabel102.setText("Usuario:");
        infoVentaPanel2.add(jLabel102, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 20, 98, -1));

        jPanel25.add(infoVentaPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 790, 80));

        orderDetailsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane13.setViewportView(orderDetailsTable);

        jPanel25.add(jScrollPane13, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 790, 240));

        jLabel103.setText("Monto total:");
        jPanel25.add(jLabel103, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 500, 70, -1));

        totalPriceDetailOrder.setEditable(false);
        jPanel25.add(totalPriceDetailOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 500, 80, -1));

        jButton27.setText("Descargar como PDF");
        jPanel25.add(jButton27, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 500, 180, -1));

        shopDetailsPane.add(jPanel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 870, 540));

        jTabbedPane1.addTab("tab8", shopDetailsPane);

        clientPane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel29.setText("Detalles del cliente:");
        jPanel13.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 190, 50));
        jPanel13.add(documentClientField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 200, -1));

        jLabel30.setText("Nro. Documento:");
        jPanel13.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 140, -1));
        jPanel13.add(nameClientField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 200, -1));

        jLabel31.setText("Email:");
        jPanel13.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 140, -1));
        jPanel13.add(emailClientField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 200, -1));

        jPanel13.add(statusClientCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 200, -1));

        jLabel35.setText("Estado:");
        jPanel13.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 140, -1));

        jLabel36.setText("Nombres:");
        jPanel13.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 140, -1));

        cleanClientFormButton.setBackground(new java.awt.Color(65, 105, 226));
        cleanClientFormButton.setForeground(new java.awt.Color(255, 255, 255));
        cleanClientFormButton.setText("Limpiar");
        cleanClientFormButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cleanClientFormButtonActionPerformed(evt);
            }
        });
        jPanel13.add(cleanClientFormButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, 200, -1));

        deleteClientButton.setBackground(new java.awt.Color(178, 34, 34));
        deleteClientButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteClientButton.setText("Eliminar");
        deleteClientButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteClientButtonActionPerformed(evt);
            }
        });
        jPanel13.add(deleteClientButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, 200, -1));

        saveClientButton.setBackground(new java.awt.Color(36, 139, 34));
        saveClientButton.setForeground(new java.awt.Color(255, 255, 255));
        saveClientButton.setText("Guardar");
        saveClientButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveClientButtonActionPerformed(evt);
            }
        });
        jPanel13.add(saveClientButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 200, -1));
        jPanel13.add(phoneClientField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 200, -1));

        jLabel33.setText("Telefono:");
        jPanel13.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 140, -1));

        clientPane.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 610));

        jPanel14.setBackground(new java.awt.Color(72, 130, 180));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel38.setText("Lista de clientes:");

        jLabel39.setText("Buscar por:");

        jButton19.setText("jButton4");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128)
                .addComponent(jLabel39)
                .addGap(18, 18, 18)
                .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(jLabel39)
                    .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton19))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel14.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 810, 60));

        clientTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                ".", ".", "Nro. Documento", "Nombres", "Correo", "Rol", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        clientTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clientTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(clientTable);
        if (clientTable.getColumnModel().getColumnCount() > 0) {
            clientTable.getColumnModel().getColumn(0).setResizable(false);
            clientTable.getColumnModel().getColumn(1).setResizable(false);
            clientTable.getColumnModel().getColumn(2).setResizable(false);
            clientTable.getColumnModel().getColumn(3).setResizable(false);
            clientTable.getColumnModel().getColumn(4).setResizable(false);
            clientTable.getColumnModel().getColumn(5).setResizable(false);
            clientTable.getColumnModel().getColumn(6).setResizable(false);
        }

        jPanel14.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 130, 810, 370));

        clientPane.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 910, 630));

        jTabbedPane1.addTab("tab9", clientPane);

        providerPane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel40.setText("Detalles del proveedor:");
        jPanel17.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 220, 50));

        jLabel45.setText("RUC");
        jPanel17.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 140, -1));
        jPanel17.add(documentProviderField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 200, -1));

        jLabel48.setText("Razon social:");
        jPanel17.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 140, -1));
        jPanel17.add(nameProviderField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 200, -1));

        jLabel51.setText("Email:");
        jPanel17.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 140, -1));
        jPanel17.add(emailProviderField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 200, -1));

        jLabel52.setText("Telefono:");
        jPanel17.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 140, -1));
        jPanel17.add(phoneProviderField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 200, -1));

        jLabel53.setText("Estado:");
        jPanel17.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 140, -1));

        jPanel17.add(statusProviderCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 200, -1));

        saveProviderButton.setBackground(new java.awt.Color(36, 139, 34));
        saveProviderButton.setForeground(new java.awt.Color(255, 255, 255));
        saveProviderButton.setText("Guardar");
        saveProviderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveProviderButtonActionPerformed(evt);
            }
        });
        jPanel17.add(saveProviderButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 200, -1));

        cleanProviderFormButton.setBackground(new java.awt.Color(65, 105, 226));
        cleanProviderFormButton.setForeground(new java.awt.Color(255, 255, 255));
        cleanProviderFormButton.setText("Limpiar");
        cleanProviderFormButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cleanProviderFormButtonActionPerformed(evt);
            }
        });
        jPanel17.add(cleanProviderFormButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, 200, -1));

        deleteProviderButton.setBackground(new java.awt.Color(178, 34, 34));
        deleteProviderButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteProviderButton.setText("Eliminar");
        deleteProviderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteProviderButtonActionPerformed(evt);
            }
        });
        jPanel17.add(deleteProviderButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, 200, -1));

        providerPane.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 610));

        jPanel18.setBackground(new java.awt.Color(72, 130, 180));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel49.setText("Lista de proveedores:");

        jLabel50.setText("Buscar por:");

        jButton24.setText("jButton4");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87)
                .addComponent(jLabel50)
                .addGap(18, 18, 18)
                .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(jLabel50)
                    .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton24))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel18.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 810, 60));

        providerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                ".", ".", "Nro. Documento", "Nombres", "Correo", "Rol", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        providerTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                providerTableMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(providerTable);
        if (providerTable.getColumnModel().getColumnCount() > 0) {
            providerTable.getColumnModel().getColumn(0).setResizable(false);
            providerTable.getColumnModel().getColumn(1).setResizable(false);
            providerTable.getColumnModel().getColumn(2).setResizable(false);
            providerTable.getColumnModel().getColumn(3).setResizable(false);
            providerTable.getColumnModel().getColumn(4).setResizable(false);
            providerTable.getColumnModel().getColumn(5).setResizable(false);
            providerTable.getColumnModel().getColumn(6).setResizable(false);
        }

        jPanel18.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 130, 810, 370));

        providerPane.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 900, 610));

        jTabbedPane1.addTab("tab10", providerPane);

        reportSellPane.setBackground(new java.awt.Color(72, 130, 180));
        reportSellPane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));
        jPanel26.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel26.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel104.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel104.setText("Reporte de compras:");
        jPanel26.add(jLabel104, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 290, -1));

        jPanel26.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 40, 160, -1));

        jLabel108.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel108.setText("Buscar por:");
        jPanel26.add(jLabel108, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 40, 80, -1));
        jPanel26.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 40, 180, -1));

        jButton4.setText("Buscar");
        jPanel26.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 40, 80, -1));

        reportSellPane.add(jPanel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 1060, 90));

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));
        jPanel27.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel27.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        reportOrderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane14.setViewportView(reportOrderTable);

        jPanel27.add(jScrollPane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 1020, 340));

        jButton5.setText("Descargar Excel");
        jPanel27.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 130, -1));

        reportSellPane.add(jPanel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 1060, 420));

        jTabbedPane1.addTab("tab11", reportSellPane);

        reportShopPane.setBackground(new java.awt.Color(73, 130, 180));
        reportShopPane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));
        jPanel28.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel28.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        reportSaleTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane15.setViewportView(reportSaleTable);

        jPanel28.add(jScrollPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 1020, 340));

        jButton7.setText("Descargar Excel");
        jPanel28.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 130, -1));

        reportShopPane.add(jPanel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 1060, 420));

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));
        jPanel29.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel29.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel107.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel107.setText("Reporte de ventas:");
        jPanel29.add(jLabel107, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 290, -1));

        jLabel106.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel106.setText("Buscar por:");
        jPanel29.add(jLabel106, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 40, 80, -1));

        jPanel29.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 40, 160, -1));
        jPanel29.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 40, 180, -1));

        jButton15.setText("Buscar");
        jPanel29.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 40, 80, -1));

        reportShopPane.add(jPanel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 1060, 90));

        jTabbedPane1.addTab("tab12", reportShopPane);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 1130, 610));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clientButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientButtonActionPerformed
        jTabbedPane1.setSelectedIndex(8);
      // TODO add your handling code here:
    }//GEN-LAST:event_clientButtonActionPerformed

    private void sellerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sellerButtonActionPerformed
        sellerSubmenu.show(sellerButton, 0, sellerButton.getHeight());
    }//GEN-LAST:event_sellerButtonActionPerformed

    private void reportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportButtonActionPerformed
        jTabbedPane1.setSelectedIndex(11);
        cleanSaleForm();
        fillReportSaleTable();
    }//GEN-LAST:event_reportButtonActionPerformed

    private void saveUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveUserButtonActionPerformed
        
        
        String currentRole =(String)userRoleCB.getSelectedItem();
        Role role = roles.stream().filter(r -> r.getName().equals(currentRole)).findFirst().get();
        
        String currentStatus = (String)userStatusCB.getSelectedItem();
        boolean status = currentStatus.equals("Activo");

        
        User user = User.builder()
                .documentNumber(userDocumentField.getText())
                .name(userNameField.getText())
                .email(userEmailField.getText())
                .role(role)
                .isEnabled(status)
                .build();
        try{
            
            if(selectedUser != 0){
                String passwordUpdate = confirmPassword();
        
                if(passwordUpdate == null) return;
                
                if(!passwordUpdate.isBlank()) user.setPassword(passwordUpdate);
                                
                user.setId(selectedUser);
                user.setPassword(passwordUpdate);
                userService.updateUser(user);
                JOptionPane.showMessageDialog(this, "Cuenta actualizada con exito!", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
                cleanUserForm();
                fillUserTable();
                return;
            }
            
            String password = confirmPassword();
            if(password == null) return;
            if(password.isBlank()){
                JOptionPane.showMessageDialog(this, "Introduzca una password!", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            user.setPassword(password);

            userService.createUser(user);
            JOptionPane.showMessageDialog(this, "Cuenta creada con exito!", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            cleanUserForm();
            fillUserTable();
        } catch(Exception e){
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(this, "Ocurrio un problema!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_saveUserButtonActionPerformed

    private String confirmPassword(){
        char[] passwordChars = userPasswordField.getPassword();
        String password = new String(passwordChars);        
        
        char[] confirmPasswordChars = confirmPasswordField.getPassword();
        String confirmPassword = new String(confirmPasswordChars);
        
        if(!password.equals(confirmPassword)){
            JOptionPane.showMessageDialog(this, "Las contrasenias no coinciden", "Verificacion de password", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return password;
    }
    
    private void cleanUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanUserButtonActionPerformed
        cleanUserForm();
    }//GEN-LAST:event_cleanUserButtonActionPerformed

    private void deleteUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteUserButtonActionPerformed
        try{
            userService.deleteUser(selectedUser);
            selectedUser = 0L;
            JOptionPane.showMessageDialog(this, "Se elimino la cuenta!", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            cleanUserForm();
            fillUserTable();
        }catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario valido.", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_deleteUserButtonActionPerformed

    private void userTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userTableMouseClicked
        int fila = userTable.rowAtPoint(evt.getPoint());
        userDocumentField.setText(userTable.getValueAt(fila, 1).toString());
        userNameField.setText(userTable.getValueAt(fila, 2).toString());
        userEmailField.setText(userTable.getValueAt(fila, 3).toString());
        
        userRoleCB.setSelectedItem(userTable.getValueAt(fila, 4).toString());
        userStatusCB.setSelectedItem(userTable.getValueAt(fila, 5).toString());
        
        selectedUser = Long.valueOf(userTable.getValueAt(fila, 0).toString());
        // TODO add your handling code here:
    }//GEN-LAST:event_userTableMouseClicked

    private void saveCategoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveCategoryButtonActionPerformed

        if(nameCategory.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Ingrese un nombre", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }       
        
        String currentStatus = (String)categoryStatusCB.getSelectedItem();
        boolean status = currentStatus.equals("Activo");
        
        Category category = Category.builder()
                .description(nameCategory.getText())
                .isEnabled(status)
                .build();
        
        if(selectedCategory != 0){
            try{            
                category.setId(selectedCategory);
                categoryService.updateCategory(category);
                JOptionPane.showMessageDialog(this, "Categoria actualizada con exito.", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
                fillCategoryTable();
                cleanCategoryForm();
                fillCategoryProductCB();
                fillProductTable();
                return;            
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "Ocurrio un error", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }      
        
        try{
            categoryService.addCategory(category);
            JOptionPane.showMessageDialog(this, "Categoria agregada.", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            fillCategoryTable();
            cleanCategoryForm();
            fillCategoryProductCB();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Ocurrio un error", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_saveCategoryButtonActionPerformed

    private void categoryTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_categoryTableMouseClicked
        int fila = categoryTable.rowAtPoint(evt.getPoint());
        nameCategory.setText(categoryTable.getValueAt(fila, 1).toString());
        categoryStatusCB.setSelectedItem(categoryTable.getValueAt(fila, 2).toString());
        selectedCategory = Long.valueOf(categoryTable.getValueAt(fila, 0).toString());
    }//GEN-LAST:event_categoryTableMouseClicked

    private void cleanCategoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanCategoryButtonActionPerformed
        cleanCategoryForm();
    }//GEN-LAST:event_cleanCategoryButtonActionPerformed

    private void deleteCategoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteCategoryButtonActionPerformed
        try{
            categoryService.deleteCategory(selectedCategory);
            JOptionPane.showMessageDialog(this, "Categoria eliminada!", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            fillCategoryTable();
            cleanCategoryForm();
            fillCategoryProductCB();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Existen productos asociados a esta categoria. Eliminelos y vuelva a intentarlo", "Aviso!", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_deleteCategoryButtonActionPerformed

    private void cleanProductButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanProductButtonActionPerformed
        cleanProductForm();
    }//GEN-LAST:event_cleanProductButtonActionPerformed

    private void saveProductButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveProductButtonActionPerformed
        if(codeProductField.getText().isBlank()){
            JOptionPane.showMessageDialog(this, "Introduzca un codigo de producto.", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if(nameProductField.getText().isBlank()){
            JOptionPane.showMessageDialog(this, "Introduzca un nombre del producto.", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if(listPriceProductField.getText().isBlank()){
            JOptionPane.showMessageDialog(this, "Introduzca el precio del producto.", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if(validatePrice() == null) return;
        
        String currentCategory =(String)categoryProductCBForm.getSelectedItem();
        Category category = categories.stream().filter(c -> c.getDescription().equals(currentCategory)).findFirst().get();
        
        String currentStatus = (String)statusProductCB.getSelectedItem();
        boolean status = currentStatus.equals("Activo");       
        
        Product product = Product.builder()
                .code(codeProductField.getText())
                .name(nameProductField.getText())
                .description(descriptionProductField.getText())
                .category(category)
                .build();                 
        
        ProductDetail productDetail = ProductDetail.builder()
                .enabled(status)
                .stock(0)
                .listPrime(validatePrice())
                .product(product)
                .build();
        
        product.setProductDetail(productDetail);
        
        try{
            if(selectedProduct!= 0){
                product.setId(selectedProduct);
                productDetail.setId(selectedProductDetail);
                productService.updateProduct(product);
                JOptionPane.showMessageDialog(this, "Producto actualizado.", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
                fillProductTable();
                cleanProductForm();
                return;
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Ocurrio un error", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try{
            productService.addProduct(product);
            JOptionPane.showMessageDialog(this, "Producto agregado.", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            fillProductTable();
            cleanProductForm();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Ocurrio un error", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
    }//GEN-LAST:event_saveProductButtonActionPerformed

    
    private BigDecimal validatePrice(){
        String price = listPriceProductField.getText().trim();
        price = price.replace(",", ".");
        BigDecimal precio;
        
        
        try {
            // Validar formato numérico con máximo dos decimales
            if (!price.matches("^\\d+(\\.\\d{1,2})?$")) {
                throw new NumberFormatException("Número con más de dos decimales o formato inválido");
            }

             precio = new BigDecimal(price);

            if (precio.compareTo(BigDecimal.ZERO) <= 0) {
                throw new NumberFormatException("El precio debe ser mayor que cero.");
            }
            
            return precio;
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Formato de precio inválido. Asegúrese de ingresar un número positivo con hasta dos decimales.\nEj: 12.34 o 12,34", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    private void productTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productTableMouseClicked
        int fila = productTable.rowAtPoint(evt.getPoint());
        
        codeProductField.setText(productTable.getValueAt(fila, 1).toString());
        nameProductField.setText(productTable.getValueAt(fila, 2).toString());
        descriptionProductField.setText(productTable.getValueAt(fila, 3).toString());
        categoryProductCBForm.setSelectedItem(productTable.getValueAt(fila, 4).toString());
        listPriceProductField.setText(productTable.getValueAt(fila, 6).toString());
        statusProductCB.setSelectedItem(productTable.getValueAt(fila, 7).toString());
        selectedProduct = Long.valueOf(productTable.getValueAt(fila, 0).toString());
        selectedProductDetail = productService.findById(selectedProduct).getProductDetail().getId();
    }//GEN-LAST:event_productTableMouseClicked

    private void deleteProductButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteProductButtonActionPerformed
        productService.deleteProduct(selectedProduct);
        JOptionPane.showMessageDialog(this, "Producto eliminado.", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
        fillProductTable();
        cleanProductForm();
    }//GEN-LAST:event_deleteProductButtonActionPerformed

    private void cleanClientFormButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanClientFormButtonActionPerformed
        cleanClientForm();
    }//GEN-LAST:event_cleanClientFormButtonActionPerformed

    private void saveClientButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveClientButtonActionPerformed
        
        String currentStatus = (String)statusClientCB.getSelectedItem();
        boolean status = currentStatus.equals("Activo");
        
        Client client = Client.builder()
                .documentNumber(documentClientField.getText())
                .name(nameClientField.getText())
                .email(emailClientField.getText())
                .phoneNumber(phoneClientField.getText())
                .isEnabled(status)
                .build();
        
        try{
            if(selectedClient != 0){
                client.setId(selectedClient);
                clientService.updateClient(client);
                JOptionPane.showMessageDialog(this, "Cliente actualizado.", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
                fillClientTable();
                cleanClientForm();
                return;
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Ocurrio un error", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try{
            clientService.addClient(client);
            JOptionPane.showMessageDialog(this, "Se agrego un cliente!", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            fillClientTable();
            cleanClientForm();
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Ocurrio un error", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_saveClientButtonActionPerformed

    private void clientTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clientTableMouseClicked
        
        int fila = clientTable.rowAtPoint(evt.getPoint());
        
        documentClientField.setText(clientTable.getValueAt(fila, 1).toString());
        nameClientField.setText(clientTable.getValueAt(fila, 2).toString());
        emailClientField.setText(clientTable.getValueAt(fila, 3).toString());
        phoneClientField.setText(clientTable.getValueAt(fila, 4).toString());
        statusClientCB.setSelectedItem(clientTable.getValueAt(fila, 5).toString());
        
        selectedClient = Long.valueOf(clientTable.getValueAt(fila, 0).toString());
    }//GEN-LAST:event_clientTableMouseClicked

    private void deleteClientButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteClientButtonActionPerformed
        clientService.deleteClient(selectedClient);
        JOptionPane.showMessageDialog(this, "Producto eliminado.", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
        fillClientTable();
        cleanClientForm();
    }//GEN-LAST:event_deleteClientButtonActionPerformed

    private void deleteProviderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteProviderButtonActionPerformed
        providerService.deleteProvider(selectedProvider);
        JOptionPane.showMessageDialog(this, "Proveedor eliminado.", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
        fillProviderTable();
        cleanProviderForm();
    }//GEN-LAST:event_deleteProviderButtonActionPerformed

    private void cleanProviderFormButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanProviderFormButtonActionPerformed
        cleanProviderForm();
    }//GEN-LAST:event_cleanProviderFormButtonActionPerformed

    private void saveProviderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveProviderButtonActionPerformed
        String currentStatus = (String)statusProviderCB.getSelectedItem();
        boolean status = currentStatus.equals("Activo");
        
        Provider provider = Provider.builder()
                .nroRuc(documentProviderField.getText())
                .razonSocial(nameProviderField.getText())
                .email(emailProviderField.getText())
                .phoneNumber(phoneProviderField.getText())
                .isEnabled(status)
                .build();
        
        try{
            if(selectedProvider != 0){
                provider.setId(selectedProvider);
                providerService.updateProvider(provider);
                JOptionPane.showMessageDialog(this, "Proveedor actualizado.", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
                fillProviderTable();
                cleanProviderForm();
                return;
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Ocurrio un error", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try{
            providerService.addProvider(provider);
            JOptionPane.showMessageDialog(this, "Se agrego un Proveedor!", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            fillProviderTable();
            cleanProviderForm();
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Ocurrio un error", "Error!", JOptionPane.ERROR_MESSAGE);
        }        
    }//GEN-LAST:event_saveProviderButtonActionPerformed

    private void providerTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_providerTableMouseClicked
        int fila = providerTable.rowAtPoint(evt.getPoint());
        
        documentProviderField.setText(providerTable.getValueAt(fila, 1).toString());
        nameProviderField.setText(providerTable.getValueAt(fila, 2).toString());
        emailProviderField.setText(providerTable.getValueAt(fila, 3).toString());
        phoneProviderField.setText(providerTable.getValueAt(fila, 4).toString());
        statusProviderCB.setSelectedItem(providerTable.getValueAt(fila, 5).toString());
        
        selectedProvider = Long.valueOf(providerTable.getValueAt(fila, 0).toString());
    }//GEN-LAST:event_providerTableMouseClicked

    private void listProductTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listProductTableMouseClicked
        int fila = listProductTable.rowAtPoint(evt.getPoint());
        Product product = productsListTableSale.stream()
                .filter(p -> p.getName().equals(listProductTable.getValueAt(fila, 1).toString()))
                .findFirst().get();
         
        
        codeProductSaleField.setText(product.getCode());
        nameProductSaleField.setText(product.getName());
        listPriceProductSaleField.setText(product.getProductDetail().getListPrime().toString());
        stockProductSaleField.setText(product.getProductDetail().getStock().toString());
        finalPriceProductSaleField.setText(product.getProductDetail().getListPrime().toString());
        int stock = product.getProductDetail().getStock();
        
        SpinnerNumberModel model = new SpinnerNumberModel(0,0,stock,1);
        quantitySaleSpinner.setModel(model);
        
        selectedProductToSale = product;
        productsListTableSale = null;
        productSelectDialog.dispose();
        
    }//GEN-LAST:event_listProductTableMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        fillListProductTable();       
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - 560) / 2;
        int y = (screenSize.height - 420) / 2;           

        productSelectDialog.setLocation(x, y);
        productSelectDialog.setSize(560,420);
        productSelectDialog.setVisible(true);
//        if(!productSaleFlag){
//            String[] listprodCaleta = {"Codigo", "Nombre","Categoria"};
//            openFilter(listProductTable,listprodCaleta, jComboBox9,jTextField17);
//            productSaleFlag = true;
//        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void addProductSaleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductSaleButtonActionPerformed
        
        if(selectedProductToSale == null){
            JOptionPane.showMessageDialog(this, "Seleccione un producto!", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if((Integer)quantitySaleSpinner.getValue()<=0){
            JOptionPane.showMessageDialog(this, "Seleccione un stock valido.", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if(discountProductSaleField.getText().isBlank()){
            discountProductSaleField.setText("0");
        }
        
        BigDecimal discount = new BigDecimal(discountProductSaleField.getText());
        BigDecimal finalPrice = new BigDecimal(finalPriceProductSaleField.getText());
        int quantity = (Integer) quantitySaleSpinner.getValue();
        SaleDetail saleDetail = SaleDetail.builder()
                .discount(discount)
                .quantity(quantity)
                .salePrice(finalPrice)
                .product(selectedProductToSale)
                .build();
        
        productsToSell.add(saleDetail);
        
        String totalSalePrice = productsToSell.stream()
                .map(sd -> sd.getSalePrice().multiply(BigDecimal.valueOf(sd.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .toString();        

        
        totalPriceSaleField.setText(totalSalePrice);
        cleanInfoProductSale();
        fillSaleTable();
    }//GEN-LAST:event_addProductSaleButtonActionPerformed

    private void listClientTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listClientTableMouseClicked
        int fila = listClientTable.rowAtPoint(evt.getPoint());
        Client client = clientListTableSale.stream()
                .filter(c -> c.getName().equals(listClientTable.getValueAt(fila, 2).toString()))
                .findFirst().get();
         
        
        docClientSaleField.setText(client.getDocumentNumber());
        nameSaleRegisterField.setText(client.getName());       
        
        selectedClientToSale = client;
        clientListTableSale = null;
        clientSelectDialog.dispose();
    }//GEN-LAST:event_listClientTableMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        fillListClientTable();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - 560) / 2;
        int y = (screenSize.height - 420) / 2;           

        clientSelectDialog.setLocation(x, y);
        clientSelectDialog.setSize(560,420);
        clientSelectDialog.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        
        if(productsToSell.isEmpty()){
            JOptionPane.showMessageDialog(this, "Agregue productos", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if(selectedClientToSale == null){
            JOptionPane.showMessageDialog(this, "Seleccione un cliente", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if(changeSaleField.getText().isBlank()){
            JOptionPane.showMessageDialog(this, "Ingrese el monto con el que paga el cliente!", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        Double change;
        Double clientAmount;
        try{
            change = Double.valueOf(changeSaleField.getText());
            clientAmount = Double.valueOf(payWithSaleField.getText());
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Ingrese el monto con el que paga el cliente!", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        
        if(change < 0){
            JOptionPane.showMessageDialog(this, "Monto invalido", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String currentDocType = (String)docTypeSaleFieldCB.getSelectedItem();
        DocumentType docType = DocumentType.FACTURA;
        
        if(currentDocType.equals("Boleta")){
            docType = DocumentType.BOLETA;
        }
        
        String totalPrice = totalPriceSaleField.getText();
        BigDecimal totalPriceToSell = new BigDecimal(totalPrice);
        
        Sales sale = Sales.builder()
                .documentType(docType)
                .user(Seller_system.currentUser)
                .client(selectedClientToSale)
                .totalAmount(totalPriceToSell)
                .saleDetail(productsToSell)
                .date(LocalDateTime.now())
                .clientAmount(clientAmount)
                .clientChange(change)
                .build();
        
        productsToSell.forEach(sd -> sd.setSale(sale));
        
        try{
            saleService.addSale(sale);
            JOptionPane.showMessageDialog(this, "La venta se realizo con exito!", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            fillReportSaleTable();
            cleanSaleForm();            
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, "No se pudo realizar la compra", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        fillListProviderTable();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - 560) / 2;
        int y = (screenSize.height - 420) / 2;           

        providerSelectDialog.setLocation(x, y);
        providerSelectDialog.setSize(560,420);
        providerSelectDialog.setVisible(true);
        
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        fillListProductBuyTable();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - 560) / 2;
        int y = (screenSize.height - 420) / 2;           

        productBuySelectDialog.setLocation(x, y);
        productBuySelectDialog.setSize(560,420);
        productBuySelectDialog.setVisible(true);
//        if(!productOrderFlag){
//            String[] listProductCaleta = {"Codigo", "Nombre","Categoria"};
//            openFilter(listProductBuyTable,listProductCaleta, jComboBox12,jTextField20);
//            productOrderFlag = true;
//        }
        
    }//GEN-LAST:event_jButton9ActionPerformed

    private void addProductSaleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductSaleButton1ActionPerformed

        if(buyPriceProductBuyField.getText().isBlank()){
            JOptionPane.showMessageDialog(this, "Introduzca un precio de compra", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(selectedProductToBuy == null){
            JOptionPane.showMessageDialog(this, "Seleccione un producto", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if((Integer)quantityBuySpinner.getValue() <= 0){
            JOptionPane.showMessageDialog(this, "Seleccione una cantidad valida", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int quantity = (Integer) quantityBuySpinner.getValue();
        BigDecimal unitPrice = new BigDecimal(buyPriceProductBuyField.getText());
        Double subtotal = Double.valueOf(subtotalBuyField.getText());
        
        OrderDetail od = OrderDetail.builder()
                .product(selectedProductToBuy)
                .quantity(quantity)
                .unitPrice(unitPrice)
                .subTotal(subtotal)
                .build();
        
        productsToBuy.add(od);
        
        Double total = productsToBuy.stream()
                .mapToDouble(OrderDetail::getSubTotal)
                .sum();
                
        
        totalPriceBuyField.setText(total.toString());
        
        fillBuyTable();
        cleanInfoProductBuy();
        
    }//GEN-LAST:event_addProductSaleButton1ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed

        if(productsToBuy.isEmpty()){
            JOptionPane.showMessageDialog(this, "No se encontraron productos que registrar", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if(selectedProviderToBuy == null){
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String currentDocType = (String)docTypeSaleFieldCB.getSelectedItem();
        DocumentType docType = DocumentType.FACTURA;
        
        if(currentDocType.equals("Boleta")){
            docType = DocumentType.BOLETA;
        }
        
        BigDecimal totalPrice = new BigDecimal(totalPriceBuyField.getText());
        
        Orders order = Orders.builder()
                .date(LocalDateTime.now())
                .documentType(docType)
                .user(Seller_system.currentUser)
                .orderDetail(productsToBuy)
                .provider(selectedProviderToBuy)
                .totalAmount(totalPrice)
                .build();
        
        productsToBuy.forEach(od -> od.setOrder(order));
        
        
        try{
            orderService.addOrder(order);
            JOptionPane.showMessageDialog(this, "La compra se realizo con exito!", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            cleanOrderForm();
            fillReportOrderTable();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, "No se pudo realizar la compra", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void listProductBuyTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listProductBuyTableMouseClicked
        int fila = listProductBuyTable.rowAtPoint(evt.getPoint());
        Product product = productsListTableOrder.stream()
                .filter(p -> p.getName().equals(listProductBuyTable.getValueAt(fila, 1).toString()))
                .findFirst().get();
         
        
        codeProductBuyField.setText(product.getCode());
        nameProductBuyField.setText(product.getName());
        stockProductBuyField.setText(product.getProductDetail().getStock().toString());
                
        selectedProductToBuy = product;
        productsListTableOrder = null;
        productBuySelectDialog.dispose();
    }//GEN-LAST:event_listProductBuyTableMouseClicked

    private void listProviderTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listProviderTableMouseClicked
        int fila = listProviderTable.rowAtPoint(evt.getPoint());
        Provider provider = providerListTableBuy.stream()
                .filter(p -> p.getNroRuc().equals(listProviderTable.getValueAt(fila, 1).toString()))
                .findFirst().get();
         
        
        rucProvBuyField.setText(provider.getNroRuc());
        nameProvBuyField.setText(provider.getRazonSocial());
                
        selectedProviderToBuy = provider;
        providerListTableBuy = null;
        providerSelectDialog.dispose();
        
    }//GEN-LAST:event_listProviderTableMouseClicked

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        Long codeSale = Long.valueOf(codeSaleField.getText());
        
        Sales sale = saleService.findById(codeSale);
        
        if(sale == null){
            JOptionPane.showMessageDialog(this, "No existe el orden de venta " + codeSale, "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        fillSaleDetailsTable(sale);
        
        
        
        LocalDateTime date = sale.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = date.format(formatter);
        
        totalPriceDetailSale.setText(sale.getTotalAmount().toString());
        clientAmountDetailsSale.setText(String.valueOf(sale.getClientAmount()));
        changeSaleDetailSale.setText(String.valueOf(sale.getClientChange()));
        dateDetailSale.setText(formattedDate);
        docTypeDetailSale.setText(sale.getDocumentType().name());
        userNameDetailSale.setText(sale.getUser().getName());
        docClientDetailSale.setText(sale.getClient().getDocumentNumber());
        nameClientDetailSale.setText(sale.getClient().getName());  
        
        
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        totalPriceDetailSale.setText("");
        clientAmountDetailsSale.setText("");
        changeSaleDetailSale.setText("");
        dateDetailSale.setText("");
        docTypeDetailSale.setText("");
        userNameDetailSale.setText("");
        docClientDetailSale.setText("");
        nameClientDetailSale.setText("");        
        modelSaleDetailsTable();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        dateDetailOrder.setText("");
        docTypeDetailOrder.setText("");
        userNameDetailOrder.setText("");
        rucDetailOrder.setText("");
        nameProviderDetailOrder.setText("");
        totalPriceDetailOrder.setText("");      
        modelOrderDetailsTable();
        
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        Long codeOrder = Long.valueOf(codeOrderField.getText());
        
        Orders order = orderService.findById(codeOrder);
        
        if(order == null){
            JOptionPane.showMessageDialog(this, "No existe el orden de compra " + codeOrder, "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        fillOrderDetailsTable(order);
        
        LocalDateTime date = order.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = date.format(formatter);
        
        totalPriceDetailOrder.setText(order.getTotalAmount().toString());
        dateDetailOrder.setText(formattedDate);
        
        docTypeDetailOrder.setText(order.getDocumentType().name());
        userNameDetailOrder.setText(order.getUser().getName());
        rucDetailOrder.setText(order.getProvider().getNroRuc());
        nameProviderDetailOrder.setText(order.getProvider().getRazonSocial()); 
    }//GEN-LAST:event_jButton23ActionPerformed

    private void saleTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saleTableMouseClicked
        int fila = saleTable.rowAtPoint(evt.getPoint());
        selectedProductSaleTable = saleTable.getValueAt(fila, 1).toString();        
    }//GEN-LAST:event_saleTableMouseClicked

    private void deleteProductSaleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteProductSaleButtonActionPerformed
        
        if(selectedProductSaleTable.isBlank()) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        productsToSell.removeIf(d -> d.getProduct().getName().equals(selectedProductSaleTable));
        
        selectedProductSaleTable = "";
        
        String totalSalePrice = productsToSell.stream()
                .map(sd -> sd.getSalePrice().multiply(BigDecimal.valueOf(sd.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .toString();        

        
        totalPriceSaleField.setText(totalSalePrice);
        fillListProductTable();
        fillSaleTable();
    }//GEN-LAST:event_deleteProductSaleButtonActionPerformed

    private void clearFilterFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearFilterFieldActionPerformed
        userFilterField.setText("");
    }//GEN-LAST:event_clearFilterFieldActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        categoryFilterField.setText("");
    }//GEN-LAST:event_jButton6ActionPerformed

    private void deleteProductSaleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteProductSaleButton1ActionPerformed
        if(selectedProductOrderTable.isBlank()) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        productsToBuy.removeIf(d -> d.getProduct().getName().equals(selectedProductOrderTable));
        
        selectedProductOrderTable = "";
        
        Double totalSalePrice = productsToBuy.stream()
                .mapToDouble(OrderDetail::getSubTotal)
                .sum();

        
        totalPriceBuyField.setText(String.valueOf(totalSalePrice));
        fillListProductTable();
        fillBuyTable();
    }//GEN-LAST:event_deleteProductSaleButton1ActionPerformed

    private void buyTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buyTableMouseClicked
        int fila = buyTable.rowAtPoint(evt.getPoint());
        selectedProductOrderTable = buyTable.getValueAt(fila, 1).toString(); 
    }//GEN-LAST:event_buyTableMouseClicked

    private void categoryButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        jTabbedPane1.setSelectedIndex(1);
        cleanOrderForm();
        cleanSaleForm();
    } 
    private void productsButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        jTabbedPane1.setSelectedIndex(2);
        cleanOrderForm();
        cleanSaleForm();
    } 
    
    private void bussinessButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        jTabbedPane1.setSelectedIndex(3);
        cleanSaleForm();
        cleanOrderForm();
    } 
    
    private void sellerRegisterButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        jTabbedPane1.setSelectedIndex(4);
        cleanOrderForm();
    } 
    
    private void sellerDetailsButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        jTabbedPane1.setSelectedIndex(5);
        cleanSaleForm();
        cleanOrderForm();
    } 
    
    private void shopRegisterButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        jTabbedPane1.setSelectedIndex(6);
        cleanSaleForm();
    } 
    
    private void shopDetailsButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        jTabbedPane1.setSelectedIndex(7);
        cleanSaleForm();
        cleanOrderForm();
    } 
    
    private void reportSellerButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        jTabbedPane1.setSelectedIndex(10);
        cleanSaleForm();
        cleanOrderForm();
        fillReportOrderTable();
    } 
    
    private void reportShopButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        jTabbedPane1.setSelectedIndex(11);
        cleanSaleForm();
        cleanOrderForm();
    } 
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserApp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aboutButton;
    private javax.swing.JButton addProductSaleButton;
    private javax.swing.JButton addProductSaleButton1;
    private javax.swing.JPanel bussinessPane;
    private javax.swing.JPanel buyInfoPanel;
    private javax.swing.JTextField buyPriceProductBuyField;
    private javax.swing.JPanel buyProductPanel;
    private javax.swing.JPanel buyProviderPanel;
    private javax.swing.JTable buyTable;
    private javax.swing.JComboBox<String> categoryFilterCB;
    private javax.swing.JTextField categoryFilterField;
    private javax.swing.JPanel categoryPane;
    private javax.swing.JLabel categoryProductCB;
    private javax.swing.JComboBox<String> categoryProductCBForm;
    private javax.swing.JComboBox<String> categoryStatusCB;
    private javax.swing.JTable categoryTable;
    private javax.swing.JTextField changeSaleDetailSale;
    private javax.swing.JTextField changeSaleField;
    private javax.swing.JButton cleanCategoryButton;
    private javax.swing.JButton cleanClientFormButton;
    private javax.swing.JButton cleanProductButton;
    private javax.swing.JButton cleanProviderFormButton;
    private javax.swing.JButton cleanUserButton;
    private javax.swing.JButton clearFilterField;
    private javax.swing.JTextField clientAmountDetailsSale;
    private javax.swing.JButton clientButton;
    private javax.swing.JComboBox<String> clientFilterOptionCB;
    private javax.swing.JTextField clientFilterOptionField;
    private javax.swing.JPanel clientPane;
    private javax.swing.JDialog clientSelectDialog;
    private javax.swing.JTable clientTable;
    private javax.swing.JTextField codeOrderField;
    private javax.swing.JTextField codeProductBuyField;
    private javax.swing.JTextField codeProductField;
    private javax.swing.JTextField codeProductSaleField;
    private javax.swing.JTextField codeSaleField;
    private javax.swing.JPasswordField confirmPasswordField;
    private javax.swing.JLabel currentUsername;
    private javax.swing.JTextField dateBuyField;
    private javax.swing.JTextField dateDetailOrder;
    private javax.swing.JTextField dateDetailSale;
    private javax.swing.JTextField dateSaleField;
    private javax.swing.JButton deleteCategoryButton;
    private javax.swing.JButton deleteClientButton;
    private javax.swing.JButton deleteProductButton;
    private javax.swing.JButton deleteProductSaleButton;
    private javax.swing.JButton deleteProductSaleButton1;
    private javax.swing.JButton deleteProviderButton;
    private javax.swing.JButton deleteUserButton;
    private javax.swing.JTextField descriptionProductField;
    private javax.swing.JTextField discountProductSaleField;
    private javax.swing.JTextField docClientDetailSale;
    private javax.swing.JTextField docClientSaleField;
    private javax.swing.JComboBox<String> docTypeBuyFieldCB;
    private javax.swing.JTextField docTypeDetailOrder;
    private javax.swing.JTextField docTypeDetailSale;
    private javax.swing.JComboBox<String> docTypeSaleFieldCB;
    private javax.swing.JTextField documentClientField;
    private javax.swing.JTextField documentProviderField;
    private javax.swing.JTextField emailClientField;
    private javax.swing.JTextField emailProviderField;
    private javax.swing.JTextField finalPriceProductSaleField;
    private javax.swing.JPanel infoClientePanel;
    private javax.swing.JPanel infoClientePanel1;
    private javax.swing.JPanel infoVentaPanel1;
    private javax.swing.JPanel infoVentaPanel2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox11;
    private javax.swing.JComboBox<String> jComboBox12;
    private javax.swing.JComboBox<String> jComboBox13;
    private javax.swing.JComboBox<String> jComboBox14;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTable listClientTable;
    private javax.swing.JTextField listPriceProductField;
    private javax.swing.JTextField listPriceProductSaleField;
    private javax.swing.JTable listProductBuyTable;
    private javax.swing.JTable listProductTable;
    private javax.swing.JTable listProviderTable;
    private javax.swing.JPopupMenu mantainerSubmenu;
    private javax.swing.JTextField nameCategory;
    private javax.swing.JTextField nameClientDetailSale;
    private javax.swing.JTextField nameClientField;
    private javax.swing.JTextField nameProductBuyField;
    private javax.swing.JTextField nameProductField;
    private javax.swing.JTextField nameProductSaleField;
    private javax.swing.JTextField nameProvBuyField;
    private javax.swing.JTextField nameProviderDetailOrder;
    private javax.swing.JTextField nameProviderField;
    private javax.swing.JTextField nameSaleRegisterField;
    private javax.swing.JTable orderDetailsTable;
    private javax.swing.JTextField payWithSaleField;
    private javax.swing.JTextField phoneClientField;
    private javax.swing.JTextField phoneProviderField;
    private javax.swing.JDialog productBuySelectDialog;
    private javax.swing.JComboBox<String> productFilterCB;
    private javax.swing.JTextField productFilterField;
    private javax.swing.JPanel productPane;
    private javax.swing.JDialog productSelectDialog;
    private javax.swing.JTable productTable;
    private javax.swing.JPanel providerPane;
    private javax.swing.JDialog providerSelectDialog;
    private javax.swing.JTable providerTable;
    private javax.swing.JSpinner quantityBuySpinner;
    private javax.swing.JSpinner quantitySaleSpinner;
    private javax.swing.JButton reportButton;
    private javax.swing.JTable reportOrderTable;
    private javax.swing.JTable reportSaleTable;
    private javax.swing.JPanel reportSellPane;
    private javax.swing.JPanel reportShopPane;
    private javax.swing.JPopupMenu reportSubmenu;
    private javax.swing.JTextField rucDetailOrder;
    private javax.swing.JTextField rucProvBuyField;
    private javax.swing.JTable saleDetailsTable;
    private javax.swing.JTable saleTable;
    private javax.swing.JButton saveCategoryButton;
    private javax.swing.JButton saveClientButton;
    private javax.swing.JButton saveProductButton;
    private javax.swing.JButton saveProviderButton;
    private javax.swing.JButton saveUserButton;
    private javax.swing.JPanel sellInfoPanel;
    private javax.swing.JPanel sellProductPanel;
    private javax.swing.JPanel sellProviderPanel;
    private javax.swing.JButton sellerButton;
    private javax.swing.JPanel sellerDetailsPane;
    private javax.swing.JPanel sellerRegisterPane;
    private javax.swing.JPopupMenu sellerSubmenu;
    private javax.swing.JPanel shopDetailsPane;
    private javax.swing.JPanel shopRegisterPane;
    private javax.swing.JPopupMenu shopSubmenu;
    private javax.swing.JComboBox<String> statusClientCB;
    private javax.swing.JComboBox<String> statusProductCB;
    private javax.swing.JComboBox<String> statusProviderCB;
    private javax.swing.JTextField stockProductBuyField;
    private javax.swing.JTextField stockProductSaleField;
    private javax.swing.JTextField subtotalBuyField;
    private javax.swing.JTextField totalPriceBuyField;
    private javax.swing.JTextField totalPriceDetailOrder;
    private javax.swing.JTextField totalPriceDetailSale;
    private javax.swing.JTextField totalPriceSaleField;
    private javax.swing.JTextField userDocumentField;
    private javax.swing.JTextField userEmailField;
    private javax.swing.JComboBox<String> userFilterCB;
    private javax.swing.JTextField userFilterField;
    private javax.swing.JTextField userNameDetailOrder;
    private javax.swing.JTextField userNameDetailSale;
    private javax.swing.JTextField userNameField;
    private javax.swing.JPanel userPane;
    private javax.swing.JPasswordField userPasswordField;
    private javax.swing.JComboBox<String> userRoleCB;
    private javax.swing.JComboBox<String> userStatusCB;
    private javax.swing.JTable userTable;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JMenuItem sellerRegister;
    private javax.swing.JMenuItem sellerDetails;
    private javax.swing.JMenuItem mantainerCategory;
    private javax.swing.JMenuItem mantainerProducts;
    private javax.swing.JMenuItem mantainerBussiness;
    private javax.swing.JMenuItem shopRegister;
    private javax.swing.JMenuItem shopDetails;
    private javax.swing.JMenuItem reportSeller;
    private javax.swing.JMenuItem reportShop;
}
