package com.sunsystem.downloadfilechatapp.downloader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by steve on 5/19/16.
 */
public class DownloadFilePresenterImpTest {
    /* Test url's */
    private static final String VALIDURL = "http://pixabay.com/static/uploads/photo/2016/05/01/01/47/turret-arch-1364314_960_720.jpg";
    private static final String VALIDURLWWW = "www.http://pixabay.com/static/uploads/photo/2016/05/01/01/47/turret-arch-1364314_960_720.jpg";
    private static final String URLNOHTTP = "://pixabay.com/static/uploads/photo/2016/05/01/01/47/turret-arch-1364314_960_720.jpg";

    private DownloadFileView mMockDownloadFileView;
    private DownloadFilePresenterImp mDownloadFilePresenterImp;

    @Before
    public void setUp() throws Exception {
        mMockDownloadFileView = mock(DownloadFileView.class);
        mDownloadFilePresenterImp = new DownloadFilePresenterImp(mMockDownloadFileView);
    }

    @After
    public void tearDown() throws Exception {
        mDownloadFilePresenterImp = null;
        mMockDownloadFileView = null;
    }

    @Test
    public void testNoUserInteraction() {
        verifyZeroInteractions(mMockDownloadFileView);
    }

    @Test
    public void displaySuccessWithValidUrl() {
        when(mMockDownloadFileView.getUrl()).thenReturn(VALIDURL);
        mDownloadFilePresenterImp.downloadFile();

        /* Verify that onDownloadSuccess() was called only 1 time */
        verify(mMockDownloadFileView, times(1)).onDownloadSuccess("Success");

        /* Very view interactions */
        verify(mMockDownloadFileView, times(1)).getUrl();
        verify(mMockDownloadFileView, never()).onDownloadFailed(anyString());
    }

    @Test
    public void displaySuccessWithValidUrlWithWWW() {
        when(mMockDownloadFileView.getUrl()).thenReturn(VALIDURLWWW);
        mDownloadFilePresenterImp.downloadFile();

        /* Verify that onDownloadSuccess() was called only 1 time */
        verify(mMockDownloadFileView, times(1)).onDownloadSuccess("Success");

        /* Very view interactions */
        verify(mMockDownloadFileView, times(1)).getUrl();
        verify(mMockDownloadFileView, never()).onDownloadFailed(anyString());

    }

    @Test
    public void displayErrorWhenUrlIsEmpty() {
        when(mMockDownloadFileView.getUrl()).thenReturn("");
        mDownloadFilePresenterImp.downloadFile();

        /* Verify that onDownloadSuccess() was called only 1 time */
        verify(mMockDownloadFileView, times(1)).onDownloadFailed("No url has been entered");

        /* Very view interactions */
        verify(mMockDownloadFileView, times(1)).getUrl();
        verify(mMockDownloadFileView, never()).onDownloadSuccess(anyString());
    }

    @Test
    public void displayErrorWhenUrlIsMissingHTTP() {
        when(mMockDownloadFileView.getUrl()).thenReturn(URLNOHTTP);
        mDownloadFilePresenterImp.downloadFile();

        /* Verify that onDownloadSuccess() was called only 1 time */
        verify(mMockDownloadFileView, times(1)).onDownloadFailed("Expected scheme name");

        /* Very view interactions */
        verify(mMockDownloadFileView, times(1)).getUrl();
        verify(mMockDownloadFileView, never()).onDownloadSuccess(anyString());
    }
}