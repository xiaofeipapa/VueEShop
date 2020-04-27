package org.xfh.mid.db.po;

/**
 * 保存所有数据库的字段和表名, 防止hardcode
 * @author cys
 *
 */
public class DBs {
	
	public static final String COMMON_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	// ==================== 表名  begin ====================
	public static final String TABLE_WAREHOUSE_STOCK = "WarehouseStock";
	public static final String TABLE_PRODUCT_SPEC_VALUE = "ProductSpecValue";
	public static final String TABLE_MODAL_SPEC_GROUP = "ModalSpecGroup";
	public static final String TABLE_MODAL_SPEC_VALUE = "ModalSpecValue";
	public static final String TABLE_PRODUCT = "Product";
	
	public static final String TABLE_PRODUCT_PARAM = "ProductParam";
	public static final String TABLE_PRODUCT_IN_BATCH = "ProductInBatch";
	public static final String TABLE_PRODUCT_IN_ITEM = "ProductInItem";
	public static final String TABLE_BACK_ROLE_MENU = "BackRoleMenu";
	public static final String TABLE_BACK_USER = "BackUser";
	
	public static final String TABLE_SUPPLIER_PRODUCT = "SupplierProduct";
	public static final String TABLE_SALE_STOCK = "SaleStock";
	public static final String TABLE_USER_ORDER = "UserOrder";
	public static final String TABLE_USER_ORDER_ITEM = "UserOrderItem";
	public static final String TABLE_CLIENT_CREDIT_ITEM = "ClientCreditItem";

	public static final String TABLE_CLIENT_BUYER = "Buyer";
	public static final String TABLE_PAYMENT_LOG = "PaymentLog";
	public static final String TABLE_USER_ORDER_ITEM_ALLOC_INFO = "UserOrderItemAllocInfo";
	public static final String TABLE_USER_ORDER_ITEM_ALLOC_INFO_PACKAGE= "UserOrderItemAllocInfoPackage";
	public static final String TABLE_WAREHOUSE_CHECK_ACTIVITY_ITEM = "UserCheckProduct";

	public static final String TABLE_HOME_FLOOR_PRODUCT = "HomeFloorProduct";
	

	
	

	// ==================== 表名  end ====================
	
	
	public static final String CREATE_USER_ID = "createUserId";
	public static final String CREATE_TIME = "createTime";
	public static final String UPDATE_USER_ID = "updateUserId";
	public static final String UPDATE_TIME = "updateTime";
	
	public static final String CONFIRM_USER_ID = "confirmUserId";			// 运营审核人
	public static final String CONFIRM_TIME = "confirmTime";				// 运营审核时间
	public static final String CONFIRM_REMARK = "confirmRemark";			// 运营审核下架或退回的理由
	
	public static final String CHECK_USER_ID = "checkUserId";			// 盘点人
	public static final String CHECK_TIME = "checkTime";				// 盘点时间
	public static final String CHECK_REMARK = "checkRemark";			// 盘点通过或退回的理由

	public static final String PRODUCT_ID = "productId";
	public static final String WAREHOUSE_ID = "warehouseId";
	public static final String STOCK = "stock";
	public static final String REMARK = "remark";

	public static final String SPEC_VALUE_ID = "specValueId";
	public static final String DATA_STATUS = "dataStatus";
	public static final String MODAL_ID = "modalId";
	public static final String STOCK_CAT = "stockCat";
	public static final String DATA_ID = "dataId";

	public static final String CAT = "cat";
	public static final String ACTION = "action";
	public static final String SPEC_VALUE_STRING = "specValueString";
	public static final String MODAL_TITLE = "modalTitle";
	public static final String MARKET_PRICE = "marketPrice";

	public static final String RETAIL_PRICE = "retailPrice";
	public static final String ONE_PRICE = "onePrice";
	public static final String SUPPLY_CAT = "supplyCat";
	public static final String SUPPLIER_ID = "supplierId";
	public static final String MENU_ID = "menuId";

	public static final String PHONE = "phone";
	public static final String BLOCK_INFO = "blockInfo";
	public static final String DISTRICT_CODE = "districtCode";
	public static final String USER_CAT = "userCat";
	public static final String USER_ID = "userId";

	public static final String LEVEL = "level";
	public static final String NAME = "name";
	public static final String PARENT_CODE = "parentCode";
	public static final String ADDRESS_CAT = "addressCat";
	public static final String DEFAULT_FLAG = "defaultFlag";

	public static final String TOTAL_STOCK = "totalStock";
	public static final String VERSION = "version";
	public static final String BUY_USER_CAT = "buyUserCat";
	public static final String BUY_USER_ID = "buyUserId";
	public static final String WAREHOUSE_STOCK = "warehouseStock";

	public static final String BACK_USER_ID = "backUserId";
	public static final String FROM_BACK = "fromBack";
	public static final String ITEM_CAT = "itemCat";
	public static final String PAY_TIME = "payTime";
	public static final String PAY_CAT = "payCat";

	public static final String FEE = "fee";
	public static final String BIZ_ID = "bizId";
	public static final String ITEM_ID = "itemId";
	public static final String ORDER_BIZ_ID = "orderBizId";

	public static final String SALE_STOCK = "saleStock";
	public static final String SUPPLIER_COUNT = "supplierCount";
	public static final String SN = "sn";
	public static final String CHECK_USER_NAME = "checkUserName";
	public static final String CHECK_USER_PHONE = "checkUserPhone";

	public static final String ACTIVITY_ID = "activityId";
	public static final String CODE = "code";
	public static final String BATCH_ID = "batchId";
	public static final String QUANTITY = "quantity";
	public static final String TO_VALUE = "toValue";

	public static final String PARENT_ID = "parentId";
	public static final String ACCOUNT = "account";
	public static final String USE_FLAG = "useFlag";
	public static final String ID = "id";
	public static final String REPAY_FLAG = "repayFlag";

	public static final String ORDER_ID = "orderId";
	public static final String SUPPLIER_PRICE = "supplierPrice";
	public static final String TOTAL_PRODUCT_FEE = "totalProductFee";
	public static final String SHOW_FRONT = "showFront";
	public static final String FLOOR_ID = "floorId";

	public static final String ORDER = "order";
	public static final String USER_PHONE = "userPhone";
	public static final String USER = "user";
	public static final String CAT_1 = "cat1";
	
	
}
