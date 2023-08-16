drop database if exists views;
create database views;

drop table if exists Customers;
drop table if exists Products;
drop table if exists Orders;
drop table if exists OrderDetails;

CREATE TABLE Customers (
    CustomerID INT PRIMARY KEY,
    CustomerName VARCHAR(255),
    Email VARCHAR(255)
);

INSERT INTO Customers (CustomerID, CustomerName, Email)
VALUES
    (1, 'John Smith', 'john@example.com'),
    (2, 'Jane Doe', 'jane@example.com'),
    (3, 'Robert Johnson', 'robert@example.com');

CREATE TABLE Products (
    ProductID INT PRIMARY KEY,
    ProductName VARCHAR(255),
    Price DECIMAL(10, 2)
);

INSERT INTO Products (ProductID, ProductName, Price)
VALUES
    (101, 'Widget A', 10.99),
    (102, 'Widget B', 15.99),
    (103, 'Widget C', 7.49);

CREATE TABLE Orders (
    OrderID INT PRIMARY KEY,
    OrderDate DATE,
    CustomerID INT,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

INSERT INTO Orders (OrderID, OrderDate, CustomerID)
VALUES
    (1001, '2023-08-01', 1),
    (1002, '2023-08-05', 2),
    (1003, '2023-08-10', 1);

CREATE TABLE OrderDetails (
    DetailID INT PRIMARY KEY,
    OrderID INT,
    ProductID INT,
    Quantity INT,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);

INSERT INTO OrderDetails (DetailID, OrderID, ProductID, Quantity)
VALUES
    (2001, 1001, 101, 3),
    (2002, 1001, 102, 2),
    (2003, 1002, 103, 5),
    (2004, 1003, 101, 1);

CREATE VIEW OrderSummary AS
SELECT
    Orders.OrderID,
    Orders.OrderDate,
    Customers.CustomerName,
    SUM(OrderDetails.Quantity) AS TotalQuantity,
    SUM(OrderDetails.Quantity * Products.Price) AS TotalCost
FROM
    Orders
        JOIN
    Customers ON Orders.CustomerID = Customers.CustomerID
        JOIN
    OrderDetails ON Orders.OrderID = OrderDetails.OrderID
        JOIN
    Products ON OrderDetails.ProductID = Products.ProductID
GROUP BY
    Orders.OrderID, Orders.OrderDate, Customers.CustomerName;


SELECT * FROM OrderSummary;
