package net.rnvn.servletx.routing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RouteHandlerTests {
	// the RouteHandler object for this test
	private static RouteHandler routeHandler;

	@BeforeAll
	public static void setUp() {
		routeHandler = new RouteHandler();
		Route usersRoute = new Route("users");
		Route myUserRoute = new Route("my/profile");
		Route usersIdRoute = new Route("<id>/profile");
		usersRoute.addChild(myUserRoute);
		usersRoute.addChild(usersIdRoute);
		routeHandler.addRoute(usersRoute);
	}

	@Test
	public void testGetMatchingRoute() {
		String url = "users/my/profile";
		Route matchedRoute = routeHandler.getMatchingRoute(url);
		assertNotNull(matchedRoute);
		assertEquals(matchedRoute.getPath(), "my/profile");
	}

	@Test
	public void testGetMatchingRouteWithVariable() {
		String url = "users/123/profile";
		Route matchedRoute = routeHandler.getMatchingRoute(url);
		assertNotNull(matchedRoute);
		assertEquals(matchedRoute.getPath(), "<id>/profile");
	}
}