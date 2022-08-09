package Ecom;

import java.util.List;

public class OrdersRequest {

	private List<OrderDetailsRequest> orders;

	public List<OrderDetailsRequest> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDetailsRequest> orders) {
		this.orders = orders;
	}

}
