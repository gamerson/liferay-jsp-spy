var jspSpy = function() {
	var findBeginSpyNode = function(node) {
		if (node == null) {
			return null;
		}

		var parent = node.parentNode;

		if (parent == null) {
			return null;
		}

		var children = parent.childNodes;

		var spyNode;

		for (var i = 0; i < children.length; i++) {
			if (children[i].nodeType == 8) {
				var comment = children[i].textContent;

				if (comment && comment.startsWith("SPY")) {
					spyNode = children[i];
				}
			}
		}

		if (spyNode) {
			return spyNode;
		}

		return findBeginSpyNode(parent);
	}

	var panelContents = {};
	var inspectedNode = $0;

	var beginSpyNode = findBeginSpyNode(inspectedNode);

	if (beginSpyNode) {
		var text = beginSpyNode.textContent;

		panelContents.jspPath = text.substring(4, text.length);
	}

	return panelContents;
}

browser.devtools.panels.elements.createSidebarPane("JSP Spy",
	function(sidebar) {
		function updateSidebar() {
			sidebar.setExpression("(" + jspSpy.toString() + ")()");
		}

		updateSidebar();

		browser.devtools.panels.elements.onSelectionChanged.addListener(updateSidebar);
	}
);