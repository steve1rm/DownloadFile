package com.sunsystem.downloadfilechatapp.downloader;

import com.sunsystem.downloadfilechatapp.downloader.data.DownloadFile;
import com.sunsystem.downloadfilechatapp.downloader.utils.DownloadResultReceiver;
import com.sunsystem.downloadfilechatapp.downloader.utils.DownloadUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

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
    private ServiceModelContract mMockServiceModelContract;
    private DownloadFile mDownloadFile;
    private DownloadResultReceiver mMockDownloadResultReceiver;

    @Before
    public void setUp() throws Exception {
        /* Happens at the start of each test */
        mMockDownloadFileView = mock(DownloadFileView.class);
        mMockServiceModelContract = mock(ServiceModelContract.class);

        mDownloadFilePresenterImp = new DownloadFilePresenterImp(mMockServiceModelContract);
        mDownloadFilePresenterImp.setView(mMockDownloadFileView);

        mMockDownloadResultReceiver = mock(DownloadResultReceiver.class);

    }

    @After
    public void tearDown() throws Exception {
        /* Happens at the end of each test */
        mDownloadFilePresenterImp = null;
        mMockDownloadFileView = null;
    }

    @Test
    public void testNoUserInteraction() {
        /* No user interactions haven't happened on the view, i.e. button click, etc */
        verifyZeroInteractions(mMockDownloadFileView);
    }

    @Test
    public void displaySuccessWithValidUrl() {
        when(mMockDownloadFileView.getUrl()).thenReturn(VALIDURL);

        /* Start the download */
        mDownloadFilePresenterImp.downloadFile();

        /* Verify view interactions */
        verify(mMockDownloadFileView, times(1)).getUrl();

        mDownloadFile = DownloadFile.getNewInstance(DownloadUtils.getFilename(VALIDURL), UUID.randomUUID(), VALIDURL);

        /* Verify that the onStartDownload was called */
        verify(mMockDownloadFileView, times(1)).onDownloadStarted(mDownloadFile);

        /* Verify that the start service was started */
        verify(mMockServiceModelContract, times(1)).startServiceDownload(mDownloadFile);

        /* Verify that onDownloadSuccess() was called only 1 time */
        verify(mMockDownloadFileView, times(1)).onDownloadSuccess(mDownloadFile);

        /* This should never be called */
        verify(mMockDownloadFileView, never()).onDownloadFailed(null, anyString());
    }

    @Test
    public void displaySuccessWithValidUrlWithWWW() {
        when(mMockDownloadFileView.getUrl()).thenReturn(VALIDURLWWW);
        mDownloadFilePresenterImp.downloadFile();

        /* Verify that onDownloadSuccess() was called only 1 time */
        verify(mMockDownloadFileView, times(1)).onDownloadSuccess(null);

        /* Very view interactions */
        verify(mMockDownloadFileView, times(1)).getUrl();
        verify(mMockDownloadFileView, never()).onDownloadFailed(null, anyString());

    }

    @Test
    public void displayErrorWhenUrlIsEmpty() {
        /* Return am empty url */
        when(mMockDownloadFileView.getUrl()).thenReturn("");
        mDownloadFilePresenterImp.downloadFile();

        /* Verify that onDownloadFailed() was called only 1 time */
        verify(mMockDownloadFileView, times(1)).onDownloadFailed(null, "No url has been entered");

        /* Very view interactions */
        verify(mMockDownloadFileView, times(1)).getUrl();
        verify(mMockDownloadFileView, never()).onDownloadSuccess(null);
    }

    @Test
    public void displayErrorWhenUrlIsMissingHTTP() {
        /* Get the url from the mocked view */
        when(mMockDownloadFileView.getUrl()).thenReturn(URLNOHTTP);
        /* Call the function to start the download */
        mDownloadFilePresenterImp.downloadFile();

        /* Verify that onDownloadFailed() was called only 1 time */
        verify(mMockDownloadFileView, times(1)).onDownloadFailed(null, "Expected scheme name");

        /* Very view interactions */
        verify(mMockDownloadFileView, times(1)).getUrl();
        verify(mMockDownloadFileView, never()).onDownloadSuccess(null);
    }

    @Test
    public void getTheCorrectFileExtensionFromUrl() {
        /* Get a correct url */
        when(mMockDownloadFileView.getUrl()).thenReturn(VALIDURL);

    }
}