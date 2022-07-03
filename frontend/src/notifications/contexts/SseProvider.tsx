import { v4 } from 'uuid';
import { createContext, useContext, useRef } from 'react';
import { SseHandler, sseHandler } from '@notifications/helpers/sse-handler';
import { useSubscribe } from '@notifications/hooks/api';
import { useUser } from '@users/contexts';
import { NotificationEventDto } from '@notifications/dtos/notification-event';
import { NotificationEvent } from '@notifications/enums/notification-event';
interface SseValues {
  subscribe: (handler: SseHandler) => string;
  unsubscribe: (id: string) => void;
}
const SseContext = createContext<SseValues>(null as any);

export const useSse = (): SseValues => useContext(SseContext);

export const SseProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const { jwt } = useUser();
  const handlersRef = useRef<Record<string, ReturnType<typeof sseHandler>>>({});

  const handleSse = (sse: NotificationEventDto<NotificationEvent>): void => {
    Object.values(handlersRef.current).forEach((handler) => handler.handle(sse));
  };
  const subscribe = (handler: SseHandler): string => {
    const id = v4();
    handlersRef.current[id] = handler;
    return id;
  };

  const unsubscribe = (id: string): void => {
    delete handlersRef.current[id];
  };

  useSubscribe(jwt, handleSse);

  return <SseContext.Provider value={{ subscribe, unsubscribe }}>{children}</SseContext.Provider>;
};
