export interface ExternalURLs {
  spotify: string;
}

export interface ImageObject {
  url: string;
  height: number | null;
  width: number | null;
}

export interface SimplifiedArtist {
  external_urls: ExternalURLs;
  href: string;
  id: string;
  name: string;
  type: string;
  uri: string;
}

export interface SimplifiedTrack {
  // based on SimplifiedTrackObject structure – extend as needed
  artists: SimplifiedArtist[];
  available_markets?: string[];
  disc_number?: number;
  duration_ms?: number;
  explicit?: boolean;
  external_urls?: ExternalURLs;
  href?: string;
  id?: string;
  is_playable?: boolean;
  name?: string;
  track_number?: number;
  type?: string;
  uri?: string;
}

export interface PagingObject<T> {
  href: string;
  limit: number;
  next: string | null;
  offset: number;
  previous: string | null;
  total: number;
  items: T[];
}

export interface Album {
  album_type: string;
  total_tracks: number;
  available_markets: string[];
  external_urls: ExternalURLs;
  href: string;
  id: string;
  images: ImageObject[];
  name: string;
  release_date: string;
  release_date_precision: 'year' | 'month' | 'day';
  restrictions?: { reason: 'market' | 'product' | 'explicit' };
  type: string;
  uri: string;
  artists: SimplifiedArtist[];
  tracks: PagingObject<SimplifiedTrack>;
}