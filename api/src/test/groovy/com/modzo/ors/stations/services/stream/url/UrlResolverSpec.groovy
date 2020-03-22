package com.modzo.ors.stations.services.stream.url

import com.modzo.ors.stations.services.stream.WebPageReader
import com.modzo.ors.stations.services.stream.scrapper.stream.DefaultStreamScrappingStrategy
import com.modzo.ors.stations.services.stream.scrapper.stream.IcyHeaderStreamScrappingStrategy
import com.modzo.ors.stations.services.stream.scrapper.stream.IcyStreamScrappingStrategy
import com.modzo.ors.stations.services.stream.scrapper.stream.StreamInfoScrappingStrategy
import com.modzo.ors.stations.services.stream.scrapper.stream.StreamScrapper
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static com.modzo.ors.stations.services.stream.scrapper.stream.StreamScrapper.Response.Format.MP3
import static com.modzo.ors.stations.services.stream.scrapper.stream.StreamScrapper.Response.Format.MPEG

class UrlResolverSpec extends Specification {

    @Shared
    List<StreamInfoScrappingStrategy> strategies = [
            new DefaultStreamScrappingStrategy(),
            new IcyStreamScrappingStrategy(),
            new IcyHeaderStreamScrappingStrategy()
    ]

    @Shared
    StreamInfoUrlGenerator generator = new StreamInfoUrlGenerator(['/info.html', '/info.html?si=1'])

    @Unroll
    void 'should use `#streamInfoUrl` to scrap stream info from `#streamUrl`'() {
        given:
            StreamScrapper scrapper = prepareScrapper(streamInfoUrl)
        when:
            StreamScrapper.Response scraped = scrapper.scrap(new StreamScrapper.Request(streamUrl)).get()
        then:
            scraped.with {
                listingStatus == 'Stream is currently up and public'
                format == MP3
                bitrate == 192
                listenerPeak == 411
                streamName == 'Radio 2.0 - Valli di Bergamo'
                genres == ['Pop', 'Rock', '80s', '70s', 'Top 40']
                website == 'www.radioduepuntozero.it'
            }
        where:
            streamUrl                       | streamInfoUrl
            'http://192.168.169.34:92100'   | 'http://192.168.169.34:92100'
            'http://192.168.169.34:92100/;' | 'http://192.168.169.34:92100/;'
            'http://192.168.169.34:92100'   | 'http://192.168.169.34:92100/info.html'
            'http://192.168.169.34:92100/;' | 'http://192.168.169.34:92100/info.html'
            'http://test.com/test/'         | 'http://test.com/test/info.html?si=1'
            'http://test.com/test'          | 'http://test.com/test/info.html?si=1'
            'http://test.com/test'          | 'http://test.com/info.html?si=1'
    }

    void 'should scrap icy pages'() {
        given:
            String url = 'http://192.168.169.34:92100';
            StreamScrapper scrapper = prepareIcyScrapper(url)
        when:
            StreamScrapper.Response scraped = scrapper.scrap(new StreamScrapper.Request(url)).get()
        then:
            scraped.with {
                listingStatus == 'Server is currently up and private.'
                format == MPEG
                bitrate == 64
                listenerPeak == 41
                streamName == 'Radio 3 - Belgrade - www.radio3.rs'
                genres == ['AC Hot']
                website == 'http://www.radio3.rs'
            }
    }

    void 'should scrap header information from stream'() {
        given:
            String url = 'http://192.168.169.34:92100';
            StreamScrapper scrapper = prepareHeaderScrapper(url)
        when:
            StreamScrapper.Response scraped = scrapper.scrap(new StreamScrapper.Request(url)).get()
        then:
            scraped.with {
                listingStatus == ''
                format == MPEG
                bitrate == 128
                listenerPeak == 0
                streamName == 'Venice Classic Radio Italia'
                genres == ['Classical']
                website == 'http://www.veniceclassicradio.eu'
            }
    }

    private StreamScrapper prepareScrapper(String url) {
        String page = getClass().getResource('/services/scrappers/stream/sample-source.html').text

        WebPageReader webPageReaderStub = Stub(WebPageReader) {
            read(url) >> Optional.of(new WebPageReader.Response(url, null, page))
        }
        return new StreamScrapper(webPageReaderStub, generator, strategies)
    }

    private StreamScrapper prepareIcyScrapper(String url) {
        String page = getClass().getResource('/services/scrappers/stream/sample-source-icy.html').text

        WebPageReader webPageReaderStub = Stub(WebPageReader) {
            read(url) >> Optional.of(new WebPageReader.Response(url, null, page))
        }
        return new StreamScrapper(webPageReaderStub, generator, strategies)
    }

    private StreamScrapper prepareHeaderScrapper(String url) {
        Map<String, List<String>> headers = [
                'content-type': ['audio/mpeg'],
                'icy-br'      : ['128'],
                'icy-genre'   : ['Classical'],
                'icy-name'    : ['Venice Classic Radio Italia'],
                'icy-notice1' : ['<BR>This stream requires <a href="http://www.winamp.com">Winamp</a><BR>'],
                'icy-notice2' : ['SHOUTcast DNAS/posix(linux x64) v2.5.5.733<BR>'],
                'icy-pub'     : ['1'],
                'icy-sr'      : ['44100'],
                'icy-url'     : ['http://www.veniceclassicradio.eu']
        ]
        WebPageReader webPageReaderStub = Stub(WebPageReader) {
            read(url) >> Optional.of(new WebPageReader.Response(url, headers, null))
        }
        return new StreamScrapper(webPageReaderStub, generator, strategies)
    }
}
