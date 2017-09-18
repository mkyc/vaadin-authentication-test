package pl.mltk.vaadinauthentication.front.events;

import java.io.Serializable;

/*
 * Event bus events used in UI are listed here as inner classes.
 */
@SuppressWarnings("serial")
public abstract class ViewEvent implements Serializable {

	public static final class NavigateToViewWithSessionStoreIDEvent {
		private final String viewName;

		private final String sessionStoreID;

		public NavigateToViewWithSessionStoreIDEvent(final String viewName, final String sessionStoreID) {
			this.viewName = viewName;
			this.sessionStoreID = sessionStoreID;
		}

		public String getViewName() {
			return viewName;
		}

		public String getSessionStoreID() {
			return sessionStoreID;
		}
	}

	public static final class NavigateToViewEvent {
		private final String viewName;

		/**
		 * @param viewName
		 */
		public NavigateToViewEvent(String viewName) {
			super();
			this.viewName = viewName;
		}

		/**
		 * @return the viewName
		 */
		public String getViewName() {
			return viewName;
		}

	}

	public static final class NavigateToExternalUIEvent {
		private final String uiName;

		/**
		 * @param uiName
		 */
		public NavigateToExternalUIEvent(String uiName) {
			super();
			this.uiName = uiName;
		}

		/**
		 * @return the uiName
		 */
		public String getUiName() {
			return uiName;
		}

	}
}
