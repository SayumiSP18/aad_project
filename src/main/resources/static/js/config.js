/*
 Each entity config describes how the generic CRUD screen should behave.
 fields marked type:'select' are populated from another entity's list
 (optionsFrom = entity key, optionsValue = id field, optionsLabel = display field).
*/

const ENTITIES = {

    zone: {
        title: "Zones", idField: "zoneId",
        listUrl: "/v1/zones/all", filterUrl: "/v1/zones/filter", filterParam: "zoneName",
        saveUrl: "/v1/zones/save", updateUrl: "/v1/zones/update", deleteUrlPrefix: "/v1/zones/",
        columns: [{ key: "zoneId", label: "ID" }, { key: "zoneName", label: "Zone Name" }],
        formFields: [{ key: "zoneName", label: "Zone Name", type: "text", required: true }]
    },

    branch: {
        title: "Branches", idField: "branchId",
        listUrl: "/v1/branches/all", filterUrl: "/v1/branches/filter", filterParam: "name",
        saveUrl: "/v1/branches/save", updateUrl: "/v1/branches/update", deleteUrlPrefix: "/v1/branches/",
        columns: [
            { key: "branchId", label: "ID" }, { key: "name", label: "Name" },
            { key: "address", label: "Address" }, { key: "zoneName", label: "Zone" }
        ],
        formFields: [
            { key: "name", label: "Branch Name", type: "text", required: true },
            { key: "address", label: "Address", type: "text", required: true },
            { key: "zoneId", label: "Zone", type: "select", optionsFrom: "zone", optionsValue: "zoneId", optionsLabel: "zoneName", required: true }
        ]
    },

    route: {
        title: "Routes", idField: "routeId",
        listUrl: "/v1/routes/all", filterUrl: "/v1/routes/filter", filterParam: "originBranchId",
        saveUrl: "/v1/routes/save", updateUrl: "/v1/routes/update", deleteUrlPrefix: "/v1/routes/",
        columns: [
            { key: "routeId", label: "ID" }, { key: "originBranchName", label: "Origin" },
            { key: "destBranchName", label: "Destination" }, { key: "distanceKm", label: "Distance (km)" }
        ],
        formFields: [
            { key: "originBranchId", label: "Origin Branch", type: "select", optionsFrom: "branch", optionsValue: "branchId", optionsLabel: "name", required: true },
            { key: "destBranchId", label: "Destination Branch", type: "select", optionsFrom: "branch", optionsValue: "branchId", optionsLabel: "name", required: true },
            { key: "distanceKm", label: "Distance (km)", type: "number", step: "0.01", required: true }
        ]
    },

    rate: {
        title: "Rates", idField: "rateId",
        listUrl: "/v1/rates/all", filterUrl: "/v1/rates/filter", filterParam: "zoneId",
        saveUrl: "/v1/rates/save", updateUrl: "/v1/rates/update", deleteUrlPrefix: "/v1/rates/",
        columns: [
            { key: "rateId", label: "ID" }, { key: "zoneName", label: "Zone" },
            { key: "weightFrom", label: "Weight From" }, { key: "weightTo", label: "Weight To" },
            { key: "pricePerKg", label: "Price/kg" }
        ],
        formFields: [
            { key: "zoneId", label: "Zone", type: "select", optionsFrom: "zone", optionsValue: "zoneId", optionsLabel: "zoneName", required: true },
            { key: "weightFrom", label: "Weight From (kg)", type: "number", step: "0.01", required: true },
            { key: "weightTo", label: "Weight To (kg)", type: "number", step: "0.01", required: true },
            { key: "pricePerKg", label: "Price per kg", type: "number", step: "0.01", required: true }
        ]
    },

    customer: {
        title: "Customers", idField: "customerId", noCreate: true,
        listUrl: "/v1/customers/all", filterUrl: "/v1/customers/filter", filterParam: "fullName",
        updateUrl: "/v1/customers/update", deleteUrlPrefix: "/v1/customers/",
        columns: [
            { key: "customerId", label: "ID" }, { key: "username", label: "Username" },
            { key: "fullName", label: "Full Name" }, { key: "address", label: "Address" }
        ],
        formFields: [
            { key: "fullName", label: "Full Name", type: "text", required: true },
            { key: "address", label: "Address", type: "text", required: true }
        ]
    },

    driver: {
        title: "Drivers", idField: "driverId",
        listUrl: "/v1/drivers/all", filterUrl: "/v1/drivers/filter", filterParam: "branchId",
        registerUrl: "/v1/drivers/register", updateUrl: "/v1/drivers/update", deleteUrlPrefix: "/v1/drivers/",
        columns: [
            { key: "driverId", label: "ID" }, { key: "username", label: "Username" },
            { key: "branchName", label: "Branch" }, { key: "licenseNo", label: "License No" }
        ],
        formFields: [
            { key: "licenseNo", label: "License No", type: "text", required: true },
            { key: "branchId", label: "Branch", type: "select", optionsFrom: "branch", optionsValue: "branchId", optionsLabel: "name", required: true }
        ],
        createFields: [
            { key: "username", label: "Username", type: "text", required: true },
            { key: "password", label: "Password", type: "password", required: true },
            { key: "licenseNo", label: "License No", type: "text", required: true },
            { key: "branchId", label: "Branch", type: "select", optionsFrom: "branch", optionsValue: "branchId", optionsLabel: "name", required: true }
        ]
    },

    vehicle: {
        title: "Vehicles", idField: "vehicleId",
        listUrl: "/v1/vehicles/all", filterUrl: "/v1/vehicles/filter", filterParam: "driverId",
        saveUrl: "/v1/vehicles/save", updateUrl: "/v1/vehicles/update", deleteUrlPrefix: "/v1/vehicles/",
        columns: [
            { key: "vehicleId", label: "ID" }, { key: "vehicleNo", label: "Vehicle No" },
            { key: "type", label: "Type" }, { key: "capacityKg", label: "Capacity (kg)" },
            { key: "driverUsername", label: "Driver" }
        ],
        formFields: [
            { key: "vehicleNo", label: "Vehicle No", type: "text", required: true },
            { key: "type", label: "Type", type: "select", staticOptions: ["BIKE", "VAN", "TRUCK"], required: true },
            { key: "capacityKg", label: "Capacity (kg)", type: "number", step: "0.01", required: true },
            { key: "driverId", label: "Driver", type: "select", optionsFrom: "driver", optionsValue: "driverId", optionsLabel: "username", required: true }
        ]
    },

    parcel: {
        title: "Parcels", idField: "parcelId",
        listUrl: "/v1/parcels/all", filterUrl: "/v1/parcels/filter", filterParam: "trackingNo",
        saveUrl: "/v1/parcels/save", updateUrl: "/v1/parcels/update", deleteUrlPrefix: "/v1/parcels/",
        columns: [
            { key: "parcelId", label: "ID" }, { key: "trackingNo", label: "Tracking No" },
            { key: "customerName", label: "Customer" }, { key: "weight", label: "Weight (kg)" },
            { key: "receiverName", label: "Receiver" }, { key: "status", label: "Status" }
        ],
        formFields: [
            { key: "customerId", label: "Customer", type: "select", optionsFrom: "customer", optionsValue: "customerId", optionsLabel: "fullName", required: true },
            { key: "weight", label: "Weight (kg)", type: "number", step: "0.01", required: true },
            { key: "description", label: "Description", type: "text" },
            { key: "receiverName", label: "Receiver Name", type: "text", required: true },
            { key: "receiverAddress", label: "Receiver Address", type: "text", required: true },
            { key: "status", label: "Status", type: "select", staticOptions: ["BOOKED", "PICKED_UP", "IN_TRANSIT", "OUT_FOR_DELIVERY", "DELIVERED", "FAILED"], editOnly: true }
        ]
    },

    booking: {
        title: "Bookings", idField: "bookingId",
        listUrl: "/v1/bookings/all", filterUrl: "/v1/bookings/filter", filterParam: "pickupBranchId",
        saveUrl: "/v1/bookings/save", updateUrl: "/v1/bookings/update", deleteUrlPrefix: "/v1/bookings/",
        columns: [
            { key: "bookingId", label: "ID" }, { key: "trackingNo", label: "Parcel" },
            { key: "pickupBranchName", label: "Pickup Branch" }, { key: "bookingDate", label: "Booking Date" },
            { key: "estimatedCost", label: "Est. Cost" }
        ],
        formFields: [
            { key: "parcelId", label: "Parcel", type: "select", optionsFrom: "parcel", optionsValue: "parcelId", optionsLabel: "trackingNo", required: true },
            { key: "pickupBranchId", label: "Pickup Branch", type: "select", optionsFrom: "branch", optionsValue: "branchId", optionsLabel: "name", required: true },
            { key: "estimatedCost", label: "Estimated Cost", type: "number", step: "0.01", required: true }
        ]
    },

    payment: {
        title: "Payments", idField: "paymentId",
        listUrl: "/v1/payments/all", filterUrl: "/v1/payments/filter", filterParam: "status",
        saveUrl: "/v1/payments/save", updateUrl: "/v1/payments/update", deleteUrlPrefix: "/v1/payments/",
        columns: [
            { key: "paymentId", label: "ID" }, { key: "bookingId", label: "Booking ID" },
            { key: "amount", label: "Amount" }, { key: "paymentMethod", label: "Method" },
            { key: "status", label: "Status" }
        ],
        formFields: [
            { key: "bookingId", label: "Booking", type: "select", optionsFrom: "booking", optionsValue: "bookingId", optionsLabel: "trackingNo", required: true },
            { key: "amount", label: "Amount", type: "number", step: "0.01", required: true },
            { key: "paymentMethod", label: "Payment Method", type: "select", staticOptions: ["CARD", "CASH", "ONLINE"], required: true },
            { key: "status", label: "Status", type: "select", staticOptions: ["PENDING", "COMPLETED", "FAILED", "REFUNDED"], editOnly: true }
        ]
    },

    invoice: {
        title: "Invoices", idField: "invoiceId",
        listUrl: "/v1/invoices/all", filterUrl: "/v1/invoices/filter", filterParam: "invoiceNo",
        saveUrl: "/v1/invoices/save", updateUrl: "/v1/invoices/update", deleteUrlPrefix: "/v1/invoices/",
        columns: [
            { key: "invoiceId", label: "ID" }, { key: "invoiceNo", label: "Invoice No" },
            { key: "paymentId", label: "Payment ID" }, { key: "issuedDate", label: "Issued Date" }
        ],
        formFields: [
            { key: "paymentId", label: "Payment", type: "select", optionsFrom: "payment", optionsValue: "paymentId", optionsLabel: "paymentId", required: true }
        ]
    },

    delivery: {
        title: "Deliveries", idField: "deliveryId",
        listUrl: "/v1/deliveries/all", filterUrl: "/v1/deliveries/filter", filterParam: "driverId",
        saveUrl: "/v1/deliveries/save", updateUrl: "/v1/deliveries/update", deleteUrlPrefix: "/v1/deliveries/",
        columns: [
            { key: "deliveryId", label: "ID" }, { key: "trackingNo", label: "Parcel" },
            { key: "driverUsername", label: "Driver" }, { key: "vehicleNo", label: "Vehicle" },
            { key: "status", label: "Status" }
        ],
        formFields: [
            { key: "parcelId", label: "Parcel", type: "select", optionsFrom: "parcel", optionsValue: "parcelId", optionsLabel: "trackingNo", required: true },
            { key: "driverId", label: "Driver", type: "select", optionsFrom: "driver", optionsValue: "driverId", optionsLabel: "username", required: true },
            { key: "vehicleId", label: "Vehicle", type: "select", optionsFrom: "vehicle", optionsValue: "vehicleId", optionsLabel: "vehicleNo", required: true },
            { key: "routeId", label: "Route (optional)", type: "select", optionsFrom: "route", optionsValue: "routeId", optionsLabel: "routeId" },
            { key: "status", label: "Status", type: "select", staticOptions: ["BOOKED", "PICKED_UP", "IN_TRANSIT", "OUT_FOR_DELIVERY", "DELIVERED", "FAILED"], editOnly: true }
        ]
    },

    trackingHistory: {
        title: "Tracking History", idField: "historyId",
        listUrl: "/v1/tracking-history/all", filterUrl: "/v1/tracking-history/filter", filterParam: "parcelId",
        saveUrl: "/v1/tracking-history/save", updateUrl: "/v1/tracking-history/update", deleteUrlPrefix: "/v1/tracking-history/",
        columns: [
            { key: "historyId", label: "ID" }, { key: "trackingNo", label: "Parcel" },
            { key: "status", label: "Status" }, { key: "location", label: "Location" },
            { key: "updatedAt", label: "Updated At" }
        ],
        formFields: [
            { key: "parcelId", label: "Parcel", type: "select", optionsFrom: "parcel", optionsValue: "parcelId", optionsLabel: "trackingNo", required: true },
            { key: "status", label: "Status", type: "text", required: true },
            { key: "location", label: "Location", type: "text", required: true }
        ]
    },

    complaint: {
        title: "Complaints", idField: "complaintId",
        listUrl: "/v1/complaints/all", filterUrl: "/v1/complaints/filter", filterParam: "customerId",
        saveUrl: "/v1/complaints/save", updateUrl: "/v1/complaints/update", deleteUrlPrefix: "/v1/complaints/",
        columns: [
            { key: "complaintId", label: "ID" }, { key: "customerName", label: "Customer" },
            { key: "trackingNo", label: "Parcel" }, { key: "description", label: "Description" },
            { key: "status", label: "Status" }
        ],
        formFields: [
            { key: "customerId", label: "Customer", type: "select", optionsFrom: "customer", optionsValue: "customerId", optionsLabel: "fullName", required: true },
            { key: "parcelId", label: "Parcel", type: "select", optionsFrom: "parcel", optionsValue: "parcelId", optionsLabel: "trackingNo", required: true },
            { key: "description", label: "Description", type: "text", required: true },
            { key: "status", label: "Status", type: "select", staticOptions: ["OPEN", "IN_PROGRESS", "RESOLVED"], editOnly: true }
        ]
    },

    notification: {
        title: "Notifications", idField: "notificationId",
        listUrl: "/v1/notifications/all", filterUrl: "/v1/notifications/filter", filterParam: "userId",
        saveUrl: "/v1/notifications/save", updateUrl: "/v1/notifications/update", deleteUrlPrefix: "/v1/notifications/",
        columns: [
            { key: "notificationId", label: "ID" }, { key: "username", label: "User" },
            { key: "message", label: "Message" }, { key: "isRead", label: "Read" },
            { key: "createdAt", label: "Created At" }
        ],
        formFields: [
            { key: "userId", label: "User", type: "select", optionsFrom: "user", optionsValue: "userId", optionsLabel: "username", required: true },
            { key: "message", label: "Message", type: "text", required: true },
            { key: "isRead", label: "Read", type: "checkbox", editOnly: true }
        ]
    },

    user: {
        title: "Users", idField: "userId", noCreate: true,
        listUrl: "/v1/users/all", filterUrl: "/v1/users/filter", filterParam: "username",
        updateUrl: "/v1/users/update", deleteUrlPrefix: "/v1/users/",
        columns: [
            { key: "userId", label: "ID" }, { key: "username", label: "Username" },
            { key: "userRoles", label: "Role" }
        ],
        formFields: [
            { key: "username", label: "Username", type: "text", required: true },
            { key: "userRoles", label: "Role", type: "select", optionsFrom: "role", optionsValue: "roleName", optionsLabel: "roleName", required: true },
            { key: "password", label: "New Password (optional)", type: "password" }
        ]
    },

    role: {
        title: "Roles", idField: "roleId", noUpdate: true,
        listUrl: "/v1/roles/all",
        saveUrl: "/v1/roles/save", deleteUrlPrefix: "/v1/roles/",
        columns: [{ key: "roleId", label: "ID" }, { key: "roleName", label: "Role Name" }],
        formFields: [{ key: "roleName", label: "Role Name", type: "text", required: true }]
    }
};

const NAV_GROUPS = [
    { label: "Operations", items: ["zone", "branch", "route", "rate"] },
    { label: "People", items: ["customer", "driver", "vehicle"] },
    { label: "Parcel Lifecycle", items: ["parcel", "booking", "payment", "invoice", "delivery", "trackingHistory", "complaint"] },
    { label: "System", items: ["notification", "user", "role"] }
];
