package com.isupatches.wisefy

import android.os.Build

import com.isupatches.wisefy.callbacks.DisconnectFromCurrentNetworkCallbacks
import com.isupatches.wisefy.constants.MISSING_PARAMETER
import com.isupatches.wisefy.internal.base.BaseUnitTest

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

import org.mockito.Mockito.mock
import org.mockito.Mockito.timeout
import org.mockito.Mockito.verify

import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Tests the ability to disconnect a device from it's current network.
 *
 * @author Patches
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
internal class DisconnectFromCurrentNetworkTests : BaseUnitTest() {

    @Test fun sync_failure_prechecks() {
        mockWiseFyPrechecksUtil.disconnectFromCurrentNetwork_failure()
        assertEquals(false, wisefy.disconnectFromCurrentNetwork())
        verificationUtil.didNotTryToDisconnectFromCurrentNetwork()
    }

    @Test fun sync_failure() {
        mockWiseFyPrechecksUtil.disconnectFromCurrentNetwork_success()
        mockNetworkUtil.disconnectFromCurrentNetwork_failure()
        assertEquals(false, wisefy.disconnectFromCurrentNetwork())
        verificationUtil.triedToDisconnectFromCurrentNetwork()
    }

    @Test fun sync_success() {
        mockWiseFyPrechecksUtil.disconnectFromCurrentNetwork_success()
        mockNetworkUtil.disconnectFromCurrentNetwork_success()
        assertEquals(true, wisefy.disconnectFromCurrentNetwork())
        verificationUtil.triedToDisconnectFromCurrentNetwork()
    }

    @Test fun async_failure_prechecks() {
        mockWiseFyPrechecksUtil.disconnectFromCurrentNetwork_failure()
        val mockCallbacks = mock(DisconnectFromCurrentNetworkCallbacks::class.java)
        wisefy.disconnectFromCurrentNetwork(mockCallbacks)
        verify(mockCallbacks, timeout(VERIFICATION_SUCCESS_TIMEOUT)).wisefyFailure(MISSING_PARAMETER)
        verificationUtil.didNotTryToDisconnectFromCurrentNetwork()
    }

    @Test fun async_failure_prechecks_nullCallback() {
        mockWiseFyPrechecksUtil.disconnectFromCurrentNetwork_failure()
        nullCallbackUtil.callDisconnectFromCurrentNetwork()
        verificationUtil.didNotTryToDisconnectFromCurrentNetwork()
    }

    @Test fun async_failure() {
        mockWiseFyPrechecksUtil.disconnectFromCurrentNetwork_success()
        mockNetworkUtil.disconnectFromCurrentNetwork_failure()
        val mockCallbacks = mock(DisconnectFromCurrentNetworkCallbacks::class.java)
        wisefy.disconnectFromCurrentNetwork(mockCallbacks)
        verify(mockCallbacks, timeout(VERIFICATION_SUCCESS_TIMEOUT)).failureDisconnectingFromCurrentNetwork()
        verificationUtil.triedToDisconnectFromCurrentNetwork()
    }

    @Test fun async_failure_nullCallback() {
        mockWiseFyPrechecksUtil.disconnectFromCurrentNetwork_success()
        mockNetworkUtil.disconnectFromCurrentNetwork_failure()
        nullCallbackUtil.callDisconnectFromCurrentNetwork()
        verificationUtil.triedToDisconnectFromCurrentNetwork()
    }

    @Test fun async_success() {
        mockWiseFyPrechecksUtil.disconnectFromCurrentNetwork_success()
        mockNetworkUtil.disconnectFromCurrentNetwork_success()
        val mockCallbacks = mock(DisconnectFromCurrentNetworkCallbacks::class.java)
        wisefy.disconnectFromCurrentNetwork(mockCallbacks)
        verify(mockCallbacks, timeout(VERIFICATION_SUCCESS_TIMEOUT)).disconnectedFromCurrentNetwork()
        verificationUtil.triedToDisconnectFromCurrentNetwork()
    }

    @Test fun async_success_nullCallback() {
        mockWiseFyPrechecksUtil.disconnectFromCurrentNetwork_success()
        mockNetworkUtil.disconnectFromCurrentNetwork_success()
        nullCallbackUtil.callDisconnectFromCurrentNetwork()
        verificationUtil.triedToDisconnectFromCurrentNetwork()
    }
}
