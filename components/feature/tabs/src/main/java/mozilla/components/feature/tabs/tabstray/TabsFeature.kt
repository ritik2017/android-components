/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package mozilla.components.feature.tabs.tabstray

import android.support.annotation.VisibleForTesting
import mozilla.components.browser.session.Session
import mozilla.components.browser.session.SessionManager
import mozilla.components.concept.tabstray.TabsTray
import mozilla.components.feature.tabs.TabsUseCases

/**
 * Feature implementation for connecting a tabs tray implementation with the session module.
 */
class TabsFeature(
    tabsTray: TabsTray,
    sessionManager: SessionManager,
    tabsUseCases: TabsUseCases,
    closeTabsTray: () -> Unit
) {
    @VisibleForTesting
    internal var presenter = TabsTrayPresenter(
        tabsTray,
        sessionManager,
        closeTabsTray
    )

    @VisibleForTesting
    internal var interactor = TabsTrayInteractor(
        tabsTray,
        tabsUseCases.selectTab,
        tabsUseCases.removeTab,
        closeTabsTray)

    fun start() {
        presenter.start()
        interactor.start()
    }

    fun stop() {
        presenter.stop()
        interactor.stop()
    }

    fun filterTabs(tabsFilter: (Session) -> Boolean) {
        presenter.sessionsFilter = tabsFilter
        presenter.calculateDiffAndUpdateTabsTray()
    }
}
