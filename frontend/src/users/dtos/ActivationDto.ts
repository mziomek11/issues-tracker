export interface ActivationTokenDto {
  activationToken: string;
}
export interface ActivateUserDto<TToken extends ActivationTokenDto> {
  userId: string;
  activationToken: TToken;
}
