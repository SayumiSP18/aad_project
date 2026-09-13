/*
 Each entity config describes how the generic CRUD screen should behave.
 "label"/"titleKey"/"singularKey" values are i18n keys, resolved via t()
 in app.js — not raw display text.
 fields marked type:'select' with optionsFrom are populated from another
 entity's live data (customer names, branch names, etc.) — that data is
 never translated, only the field's own label is.
 fields marked type:'select' with staticOptions carry an optionsNamespace
 so app.js can translate each option value (e.g. "BOOKED" -> status.BOOKED).
*/

const ENTITIES = {

    zone: {
        titleKey: "entity.zone.title", singularKey: "entity.zone.singular", idField: "zoneId",
        listUrl: "/v1/zones/all", filterUrl: "/v1/zones/filter", filterParam: "zoneName",
        saveUrl: "/v1/zones/save", updateUrl: "/v1/zones/update", deleteUrlPrefix: "/v1/zones/",
        columns: [{ key: "zoneId", label: "common.id" }, { key: "zoneName", label: "field.zoneName" }],
        formFields: [{ key: "zoneName", label: "field.zoneName", type: "text", required: true }]
    },

    branch: {
        titleKey: "entity.branch.title", singularKey: "entity.branch.singular", idField: "branchId",
        listUrl: "/v1/branches/all", filterUrl: "/v1/branches/filter", filterParam: "name",
        saveUrl: "/v1/branches/save", updateUrl: "/v1/branches/update", deleteUrlPrefix: "/v1/branches/",
        columns: [
            { key: "branchId", label: "common.id" }, { key: "name", label: "common.name" },
            { key: "address", label: "common.address" }, { key: "zoneName", label: "common.zone" }
        ],
        formFields: [
            { key: "name", label: "field.branchName", type: "text", required: true },
            { key: "address", label: "common.address", type: "text", required: true },
            { key: "zoneId", label: "common.zone", type: "select", optionsFrom: "zone", optionsValue: "zoneId", optionsLabel: "zoneName", required: true }
        ]
    },

    route: {
        titleKey: "entity.route.title", singularKey: "entity.route.singular", idField: "routeId",
        listUrl: "/v1/routes/all", filterUrl: "/v1/routes/filter", filterParam: "originBranchId",
        saveUrl: "/v1/routes/save", updateUrl: "/v1/routes/update", deleteUrlPrefix: "/v1/routes/",
        columns: [
            { key: "routeId", label: "common.id" }, { key: "originBranchName", label: "field.origin" },
            { key: "destBranchName", label: "field.destination" }, { key: "distanceKm", label: "field.distanceKm" }
        ],
        formFields: [
            { key: "originBranchId", label: "field.originBranch", type: "select", optionsFrom: "branch", optionsValue: "branchId", optionsLabel: "name", required: true },
            { key: "destBranchId", label: "field.destinationBranch", type: "select", optionsFrom: "branch", optionsValue: "branchId", optionsLabel: "name", required: true },
            { key: "distanceKm", label: "field.distanceKm", type: "number", step: "0.01", required: true }
        ]
    },

    rate: {
        titleKey: "entity.rate.title", singularKey: "entity.rate.singular", idField: "rateId",
        listUrl: "/v1/rates/all", filterUrl: "/v1/rates/filter", filterParam: "zoneId",
        saveUrl: "/v1/rates/save", updateUrl: "/v1/rates/update", deleteUrlPrefix: "/v1/rates/",
        columns: [
            { key: "rateId", label: "common.id" }, { key: "zoneName", label: "common.zone" },
            { key: "weightFrom", label: "field.weightFrom" }, { key: "weightTo", label: "field.weightTo" },
            { key: "pricePerKg", label: "field.pricePerKg" }
        ],
        formFields: [
            { key: "zoneId", label: "common.zone", type: "select", optionsFrom: "zone", optionsValue: "zoneId", optionsLabel: "zoneName", required: true },
            { key: "weightFrom", label: "field.weightFromKg", type: "number", step: "0.01", required: true },
            { key: "weightTo", label: "field.weightToKg", type: "number", step: "0.01", required: true },
            { key: "pricePerKg", label: "field.pricePerKgForm", type: "number", step: "0.01", required: true }
        ]
    },

    customer: {
        titleKey: "entity.customer.title", singularKey: "entity.customer.singular", idField: "customerId", noCreate: true,
        listUrl: "/v1/customers/all", filterUrl: "/v1/customers/filter", filterParam: "fullName",
        updateUrl: "/v1/customers/update", deleteUrlPrefix: "/v1/customers/",
        columns: [
            { key: "customerId", label: "common.id" }, { key: "username", label: "common.username" },
            { key: "fullName", label: "field.fullName" }, { key: "address", label: "common.address" }
        ],
        formFields: [
            { key: "fullName", label: "field.fullName", type: "text", required: true },
            { key: "address", label: "common.address", type: "text", required: true }
        ]
    },

    driver: {
        titleKey: "entity.driver.title", singularKey: "entity.driver.singular", idField: "driverId",
        listUrl: "/v1/drivers/all", filterUrl: "/v1/drivers/filter", filterParam: "branchId",
        registerUrl: "/v1/drivers/register", updateUrl: "/v1/drivers/update", deleteUrlPrefix: "/v1/drivers/",
        columns: [
            { key: "driverId", label: "common.id" }, { key: "username", label: "common.username" },
            { key: "branchName", label: "field.branch" }, { key: "licenseNo", label: "field.licenseNo" }
        ],
        formFields: [
            { key: "licenseNo", label: "field.licenseNo", type: "text", required: true },
            { key: "branchId", label: "field.branch", type: "select", optionsFrom: "branch", optionsValue: "branchId", optionsLabel: "name", required: true }
        ],
        createFields: [
            { key: "username", label: "common.username", type: "text", required: true },
            { key: "password", label: "common.password", type: "password", required: true },
            { key: "licenseNo", label: "field.licenseNo", type: "text", required: true },
            { key: "branchId", label: "field.branch", type: "select", optionsFrom: "branch", optionsValue: "branchId", optionsLabel: "name", required: true }
        ]
    },

    vehicle: {
        titleKey: "entity.vehicle.title", singularKey: "entity.vehicle.singular", idField: "vehicleId",
        listUrl: "/v1/vehicles/all", filterUrl: "/v1/vehicles/filter", filterParam: "driverId",
        saveUrl: "/v1/vehicles/save", updateUrl: "/v1/vehicles/update", deleteUrlPrefix: "/v1/vehicles/",
        columns: [
            { key: "vehicleId", label: "common.id" }, { key: "vehicleNo", label: "field.vehicleNo" },
            { key: "type", label: "common.type" }, { key: "capacityKg", label: "field.capacityKg" },
            { key: "driverUsername", label: "field.driver" }
        ],
        formFields: [
            { key: "vehicleNo", label: "field.vehicleNo", type: "text", required: true },
            { key: "type", label: "common.type", type: "select", staticOptions: ["BIKE", "VAN", "TRUCK"], optionsNamespace: "vehicleType", required: true },
            { key: "capacityKg", label: "field.capacityKg", type: "number", step: "0.01", required: true },
            { key: "driverId", label: "field.driver", type: "select", optionsFrom: "driver", optionsValue: "driverId", optionsLabel: "username", required: true }
        ]
    },

    parcel: {
        titleKey: "entity.parcel.title", singularKey: "entity.parcel.singular", idField: "parcelId",
        listUrl: "/v1/parcels/all", filterUrl: "/v1/parcels/filter", filterParam: "trackingNo",
        saveUrl: "/v1/parcels/save", updateUrl: "/v1/parcels/update", deleteUrlPrefix: "/v1/parcels/",
        columns: [
            { key: "parcelId", label: "common.id" }, { key: "trackingNo", label: "field.trackingNo" },
            { key: "customerName", label: "field.customer" }, { key: "weight", label: "field.weightKg" },
            { key: "receiverName", label: "field.receiver" }, { key: "status", label: "common.status" }
        ],
        formFields: [
            { key: "customerId", label: "field.customer", type: "select", optionsFrom: "customer", optionsValue: "customerId", optionsLabel: "fullName", required: true },
            { key: "weight", label: "field.weightKg", type: "number", step: "0.01", required: true },
            { key: "description", label: "common.description", type: "text" },
            { key: "receiverName", label: "field.receiverName", type: "text", required: true },
            { key: "receiverAddress", label: "field.receiverAddress", type: "text", required: true },
            { key: "status", label: "common.status", type: "select", staticOptions: ["BOOKED", "PICKED_UP", "IN_TRANSIT", "OUT_FOR_DELIVERY", "DELIVERED", "FAILED"], optionsNamespace: "status", editOnly: true }
        ]
    },

    booking: {
        titleKey: "entity.booking.title", singularKey: "entity.booking.singular", idField: "bookingId",
        listUrl: "/v1/bookings/all", filterUrl: "/v1/bookings/filter", filterParam: "pickupBranchId",
        saveUrl: "/v1/bookings/save", updateUrl: "/v1/bookings/update", deleteUrlPrefix: "/v1/bookings/",
        columns: [
            { key: "bookingId", label: "common.id" }, { key: "trackingNo", label: "field.parcel" },
            { key: "pickupBranchName", label: "field.pickupBranch" }, { key: "bookingDate", label: "field.bookingDate" },
            { key: "estimatedCost", label: "field.estCost" }
        ],
        formFields: [
            { key: "parcelId", label: "field.parcel", type: "select", optionsFrom: "parcel", optionsValue: "parcelId", optionsLabel: "trackingNo", required: true },
            { key: "pickupBranchId", label: "field.pickupBranch", type: "select", optionsFrom: "branch", optionsValue: "branchId", optionsLabel: "name", required: true },
            { key: "estimatedCost", label: "field.estimatedCost", type: "number", step: "0.01", required: true }
        ]
    },

    payment: {
        titleKey: "entity.payment.title", singularKey: "entity.payment.singular", idField: "paymentId",
        listUrl: "/v1/payments/all", filterUrl: "/v1/payments/filter", filterParam: "status",
        saveUrl: "/v1/payments/save", updateUrl: "/v1/payments/update", deleteUrlPrefix: "/v1/payments/",
        columns: [
            { key: "paymentId", label: "common.id" }, { key: "bookingId", label: "field.bookingId" },
            { key: "amount", label: "field.amount" }, { key: "paymentMethod", label: "field.method" },
            { key: "status", label: "common.status" }
        ],
        formFields: [
            { key: "bookingId", label: "field.booking", type: "select", optionsFrom: "booking", optionsValue: "bookingId", optionsLabel: "trackingNo", required: true },
            { key: "amount", label: "field.amount", type: "number", step: "0.01", required: true },
            { key: "paymentMethod", label: "field.paymentMethod", type: "select", staticOptions: ["CARD", "CASH", "ONLINE"], optionsNamespace: "paymentMethodOpt", required: true },
            { key: "status", label: "common.status", type: "select", staticOptions: ["PENDING", "COMPLETED", "FAILED", "REFUNDED"], optionsNamespace: "paymentStatus", editOnly: true }
        ]
    },

    invoice: {
        titleKey: "entity.invoice.title", singularKey: "entity.invoice.singular", idField: "invoiceId",
        listUrl: "/v1/invoices/all", filterUrl: "/v1/invoices/filter", filterParam: "invoiceNo",
        saveUrl: "/v1/invoices/save", updateUrl: "/v1/invoices/update", deleteUrlPrefix: "/v1/invoices/",
        columns: [
            { key: "invoiceId", label: "common.id" }, { key: "invoiceNo", label: "field.invoiceNo" },
            { key: "paymentId", label: "field.paymentId" }, { key: "issuedDate", label: "field.issuedDate" }
        ],
        formFields: [
            { key: "paymentId", label: "field.payment", type: "select", optionsFrom: "payment", optionsValue: "paymentId", optionsLabel: "paymentId", required: true }
        ]
    },

    delivery: {
        titleKey: "entity.delivery.title", singularKey: "entity.delivery.singular", idField: "deliveryId",
        listUrl: "/v1/deliveries/all", filterUrl: "/v1/deliveries/filter", filterParam: "driverId",
        saveUrl: "/v1/deliveries/save", updateUrl: "/v1/deliveries/update", deleteUrlPrefix: "/v1/deliveries/",
        columns: [
            { key: "deliveryId", label: "common.id" }, { key: "trackingNo", label: "field.parcel" },
            { key: "driverUsername", label: "field.driver" }, { key: "vehicleNo", label: "field.vehicle" },
            { key: "status", label: "common.status" }
        ],
        formFields: [
            { key: "parcelId", label: "field.parcel", type: "select", optionsFrom: "parcel", optionsValue: "parcelId", optionsLabel: "trackingNo", required: true },
            { key: "driverId", label: "field.driver", type: "select", optionsFrom: "driver", optionsValue: "driverId", optionsLabel: "username", required: true },
            { key: "vehicleId", label: "field.vehicle", type: "select", optionsFrom: "vehicle", optionsValue: "vehicleId", optionsLabel: "vehicleNo", required: true },
            { key: "routeId", label: "field.routeOptional", type: "select", optionsFrom: "route", optionsValue: "routeId", optionsLabel: "routeId" },
            { key: "status", label: "common.status", type: "select", staticOptions: ["BOOKED", "PICKED_UP", "IN_TRANSIT", "OUT_FOR_DELIVERY", "DELIVERED", "FAILED"], optionsNamespace: "status", editOnly: true }
        ]
    },

    trackingHistory: {
        titleKey: "entity.trackingHistory.title", singularKey: "entity.trackingHistory.singular", idField: "historyId",
        listUrl: "/v1/tracking-history/all", filterUrl: "/v1/tracking-history/filter", filterParam: "parcelId",
        saveUrl: "/v1/tracking-history/save", updateUrl: "/v1/tracking-history/update", deleteUrlPrefix: "/v1/tracking-history/",
        columns: [
            { key: "historyId", label: "common.id" }, { key: "trackingNo", label: "field.parcel" },
            { key: "status", label: "common.status" }, { key: "location", label: "common.location" },
            { key: "updatedAt", label: "common.updatedAt" }
        ],
        formFields: [
            { key: "parcelId", label: "field.parcel", type: "select", optionsFrom: "parcel", optionsValue: "parcelId", optionsLabel: "trackingNo", required: true },
            { key: "status", label: "common.status", type: "text", required: true },
            { key: "location", label: "common.location", type: "text", required: true }
        ]
    },

    complaint: {
        titleKey: "entity.complaint.title", singularKey: "entity.complaint.singular", idField: "complaintId",
        listUrl: "/v1/complaints/all", filterUrl: "/v1/complaints/filter", filterParam: "customerId",
        saveUrl: "/v1/complaints/save", updateUrl: "/v1/complaints/update", deleteUrlPrefix: "/v1/complaints/",
        columns: [
            { key: "complaintId", label: "common.id" }, { key: "customerName", label: "field.customer" },
            { key: "trackingNo", label: "field.parcel" }, { key: "description", label: "common.description" },
            { key: "status", label: "common.status" }
        ],
        formFields: [
            { key: "customerId", label: "field.customer", type: "select", optionsFrom: "customer", optionsValue: "customerId", optionsLabel: "fullName", required: true },
            { key: "parcelId", label: "field.parcel", type: "select", optionsFrom: "parcel", optionsValue: "parcelId", optionsLabel: "trackingNo", required: true },
            { key: "description", label: "common.description", type: "text", required: true },
            { key: "status", label: "common.status", type: "select", staticOptions: ["OPEN", "IN_PROGRESS", "RESOLVED"], optionsNamespace: "complaintStatus", editOnly: true }
        ]
    },

    notification: {
        titleKey: "entity.notification.title", singularKey: "entity.notification.singular", idField: "notificationId",
        listUrl: "/v1/notifications/all", filterUrl: "/v1/notifications/filter", filterParam: "userId",
        saveUrl: "/v1/notifications/save", updateUrl: "/v1/notifications/update", deleteUrlPrefix: "/v1/notifications/",
        columns: [
            { key: "notificationId", label: "common.id" }, { key: "username", label: "field.user" },
            { key: "message", label: "field.message" }, { key: "isRead", label: "field.isRead" },
            { key: "createdAt", label: "common.createdAt" }
        ],
        formFields: [
            { key: "userId", label: "field.user", type: "select", optionsFrom: "user", optionsValue: "userId", optionsLabel: "username", required: true },
            { key: "message", label: "field.message", type: "text", required: true },
            { key: "isRead", label: "field.isRead", type: "checkbox", editOnly: true }
        ]
    },

    user: {
        titleKey: "entity.user.title", singularKey: "entity.user.singular", idField: "userId", noCreate: true,
        listUrl: "/v1/users/all", filterUrl: "/v1/users/filter", filterParam: "username",
        updateUrl: "/v1/users/update", deleteUrlPrefix: "/v1/users/",
        columns: [
            { key: "userId", label: "common.id" }, { key: "username", label: "common.username" },
            { key: "userRoles", label: "field.role" }
        ],
        formFields: [
            { key: "username", label: "common.username", type: "text", required: true },
            { key: "userRoles", label: "field.role", type: "select", optionsFrom: "role", optionsValue: "roleName", optionsLabel: "roleName", required: true },
            { key: "password", label: "field.newPasswordOptional", type: "password" }
        ]
    },

    role: {
        titleKey: "entity.role.title", singularKey: "entity.role.singular", idField: "roleId", noUpdate: true,
        listUrl: "/v1/roles/all",
        saveUrl: "/v1/roles/save", deleteUrlPrefix: "/v1/roles/",
        columns: [{ key: "roleId", label: "common.id" }, { key: "roleName", label: "field.roleName" }],
        formFields: [{ key: "roleName", label: "field.roleName", type: "text", required: true }]
    }
};

const NAV_GROUPS = [
    { labelKey: "nav.groups.operations", items: ["zone", "branch", "route", "rate"] },
    { labelKey: "nav.groups.people", items: ["customer", "driver", "vehicle"] },
    { labelKey: "nav.groups.parcelLifecycle", items: ["parcel", "booking", "payment", "invoice", "delivery", "trackingHistory", "complaint"] },
    { labelKey: "nav.groups.system", items: ["notification", "user", "role"] }
];