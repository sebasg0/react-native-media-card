#ifdef RCT_NEW_ARCH_ENABLED
#import "MediaCardView.h"
#import <react/renderer/components/RNMediaCardViewSpec/ComponentDescriptors.h>
#import <react/renderer/components/RNMediaCardViewSpec/EventEmitters.h>
#import <react/renderer/components/RNMediaCardViewSpec/Props.h>
#import <react/renderer/components/RNMediaCardViewSpec/RCTComponentViewHelpers.h>

#import "RCTFabricComponentsPlugins.h"
#import "Utils.h"

using namespace facebook::react;

@interface MediaCardView () <RCTMediaCardViewViewProtocol>

@end

@implementation MediaCardView {
    UIView * _view;
}

+ (ComponentDescriptorProvider)componentDescriptorProvider
{
    return concreteComponentDescriptorProvider<MediaCardViewComponentDescriptor>();
}

- (instancetype)initWithFrame:(CGRect)frame
{
  if (self = [super initWithFrame:frame]) {
    static const auto defaultProps = std::make_shared<const MediaCardViewProps>();
    _props = defaultProps;

    _view = [[UIView alloc] init];

    self.contentView = _view;
  }

  return self;
}

- (void)updateProps:(Props::Shared const &)props oldProps:(Props::Shared const &)oldProps
{
    const auto &oldViewProps = *std::static_pointer_cast<MediaCardViewProps const>(_props);
    const auto &newViewProps = *std::static_pointer_cast<MediaCardViewProps const>(props);

    if (oldViewProps.color != newViewProps.color) {
        NSString * colorToConvert = [[NSString alloc] initWithUTF8String: newViewProps.color.c_str()];
        [_view setBackgroundColor: [Utils hexStringToColor:colorToConvert]];
    }

    [super updateProps:props oldProps:oldProps];
}

Class<RCTComponentViewProtocol> MediaCardViewCls(void)
{
    return MediaCardView.class;
}

@end

#else

#import "MediaCardView.h"
#import <AVFoundation/AVFoundation.h>
#import <SDWebImage/SDWebImage.h>
#import <AVKit/AVKit.h>

@implementation MediaCardView {
    SDAnimatedImageView *_imageView;
    AVPlayer *_player;
    AVPlayerLayer *_playerLayer;
    UIButton *_playPauseButton;
}

- (instancetype)init {
    if (self = [super init]) {
        _imageView = [[SDAnimatedImageView alloc] init];
        [self addSubview:_imageView];
        
        _player = [[AVPlayer alloc] init];
        _playerLayer = [AVPlayerLayer playerLayerWithPlayer:_player];
        [self.layer addSublayer:_playerLayer];
        
        // Initialize and configure the play/pause button
        _playPauseButton = [UIButton buttonWithType:UIButtonTypeSystem];
        [_playPauseButton setTitle:@"Pause" forState:UIControlStateNormal];
        [_playPauseButton addTarget:self action:@selector(playPauseToggled:) forControlEvents:UIControlEventTouchUpInside];
        [self addSubview:_playPauseButton];
    }
    return self;
}

- (void)layoutSubviews {
    [super layoutSubviews];
    _imageView.frame = self.bounds;
    _playerLayer.frame = self.bounds;
    _playPauseButton.frame = CGRectMake(10, 10, 100, 40); // Adjust the frame as per your requirement
}

- (void)setMediaUrl:(NSString *)mediaUrl {
    _mediaUrl = mediaUrl;
    
    // Configure the audio session for playback
    [[AVAudioSession sharedInstance] setCategory:AVAudioSessionCategoryPlayback error:nil];
    [[AVAudioSession sharedInstance] setActive:YES error:nil];

    // Determine the type of media (e.g., image, GIF, video) based on the URL or file type.
    if ([_mediaUrl hasSuffix:@".mp4"] || [_mediaUrl hasSuffix:@".mov"]) {
        // Handle video

        _imageView.hidden = YES;
        _playerLayer.hidden = NO;
        _playPauseButton.hidden = NO; // Show the play/pause button for video
        
        AVPlayerItem *playerItem = [AVPlayerItem playerItemWithURL:[NSURL URLWithString:_mediaUrl]];
        [_player replaceCurrentItemWithPlayerItem:playerItem];
        [_player play];
        3
        // Loop video if needed
        if (self.loopCount != 0) {
            [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(playerItemDidReachEnd:) name:AVPlayerItemDidPlayToEndTimeNotification object:[_player currentItem]];
        }
    } else {
        // Handle image/GIF
        _imageView.hidden = NO;
        _playerLayer.hidden = YES;
        _playPauseButton.hidden = YES; // Hide the play/pause button for image/GIF
        
        [_imageView sd_setImageWithURL:[NSURL URLWithString:_mediaUrl] completed:^(UIImage * _Nullable image, NSError * _Nullable error, SDImageCacheType cacheType, NSURL * _Nullable imageURL) {
            if ([image isKindOfClass:[SDAnimatedImage class]]) {
                SDAnimatedImage *animatedImage = (SDAnimatedImage *)image;
                self->_imageView.animationRepeatCount = self.loopCount; // or set to 1 to play only once
                if (!self->_imageView.isAnimating) {
                    [self->_imageView startAnimating];
                }
            }
        }];
    }
}

- (void)playPauseToggled:(UIButton *)sender {
    if (_player.rate == 0) { // If player is paused
        [_player play]; // Play the video
        [sender setTitle:@"Pause" forState:UIControlStateNormal]; // Update button title
    } else {
        [_player pause]; // Pause the video
        [sender setTitle:@"Play" forState:UIControlStateNormal]; // Update button title
    }
}

- (void)playerItemDidReachEnd:(NSNotification *)notification {
    [_player seekToTime:kCMTimeZero];
    [_player play];
}


@end


#endif
