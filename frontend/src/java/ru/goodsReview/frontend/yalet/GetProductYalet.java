package ru.goodsReview.frontend.yalet;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;

import ru.goodsReview.core.model.Product;
import ru.goodsReview.frontend.model.ProductForView;

// todo rewrite this class
public class GetProductYalet implements Yalet {
	public void process(InternalRequest req, InternalResponse res) {
		String request = req.getParameter("id");
		long id;
		try {
			id = Long.parseLong(request);
		} catch (Exception e) {
			Product product = new Product(1, "Error");
			ProductForView pvf = new ProductForView(product);
			res.add(pvf);
			return;
		}
		if (request != null && !request.isEmpty()) {
			Product product = new Product(id, "All is ok" + id);
			ProductForView pvf = new ProductForView(product);
			res.add(pvf);
			return;
		}
		Product product = new Product(1, "Error");
			ProductForView pvf = new ProductForView(product);
			res.add(pvf);
			return;
		/**/
	}
}