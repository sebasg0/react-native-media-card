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
#endif
