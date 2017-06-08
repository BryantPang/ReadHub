package com.chihopang.readhub.network;

import com.chihopang.readhub.model.Sponsor;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.GET;

public interface SponsorService {
  @GET("BryantPang/ReadHub/master/sponsor.json") Observable<List<Sponsor>> getSponsors();
}
