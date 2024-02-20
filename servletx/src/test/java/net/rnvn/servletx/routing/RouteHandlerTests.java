package net.rnvn.servletx.routing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RouteHandlerTests {
	// the RouteHandler object for this test
	private static RouteHandler routeHandler;

	@BeforeAll
	static void setUp() {
		routeHandler = new RouteHandler();
		Route usersRoute = new Route("users");
		Route myUserRoute = new Route("my/profile");
		Route usersIdRoute = new Route("<id>/profile");
		RouteMapping myUserRouteMapping = new RouteMapping(myUserRoute, null);
		RouteMapping usersIdRouteMapping = new RouteMapping(usersIdRoute, null);
		RouteMapping usersRouteMapping = new RouteMapping(usersRoute, null);
		usersRouteMapping.addChild(myUserRouteMapping);
		usersRouteMapping.addChild(usersIdRouteMapping);
		routeHandler.addRouteMapping(usersRouteMapping);
	}

	@Test
	void testGetMatchingRoute() {
		String url = "users/my/profile";
		RouteMapping matchedRouteMapping = routeHandler.getFirstMatchingRouteMapping(url);
		assertNotNull(matchedRouteMapping);
		assertEquals(matchedRouteMapping.getRoute().getPath(), "my/profile");
	}

	@Test
	void testGetMatchingRouteWithVariable() {
		String url = "users/123/profile";
		RouteMapping matchedRouteMapping = routeHandler.getFirstMatchingRouteMapping(url);
		assertNotNull(matchedRouteMapping);
		assertEquals(matchedRouteMapping.getRoute().getPath(), "<id>/profile");
	}
}